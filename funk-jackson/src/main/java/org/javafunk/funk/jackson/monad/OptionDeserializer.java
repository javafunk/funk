/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */

package org.javafunk.funk.jackson.monad;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.monads.Option;

import java.io.IOException;
import java.util.concurrent.Callable;

import static org.javafunk.funk.monads.Option.none;
import static org.javafunk.funk.monads.Option.option;

public class OptionDeserializer extends StdDeserializer<Option<?>> implements ContextualDeserializer {
    protected final JavaType referenceType;
    protected final Option<JsonDeserializer<?>> valueHandler;
    protected final Option<TypeDeserializer> typeHandler;

    public OptionDeserializer(JavaType valueType, Option<TypeDeserializer> typeHandler, Option<JsonDeserializer<?>> valueHandler) {
        super(valueType);
        referenceType = valueType.containedType(0);
        this.typeHandler = typeHandler;
        this.valueHandler = valueHandler;
    }

    @Override
    public Option<?> getNullValue() {
        return none();
    }

    @Override
    public Option<?> deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        Object reference = null;

        if (valueHandler.hasValue()) {
            if (typeHandler.hasNoValue()) {
                reference = valueHandler.get().deserialize(jp, context);
            } else {
                reference = valueHandler.get().deserializeWithType(jp, context, typeHandler.get());
            }
        }

        return option(reference);
    }

    /**
     * Method called to finalize setup of this deserializer, after deserializer itself has been registered. This is
     * needed to handle recursive and transitive dependencies.
     */
    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext context, final BeanProperty property) throws JsonMappingException {
        Option<JsonDeserializer<?>> contextualValueHandler = valueHandler.or(new Callable<Option<? extends JsonDeserializer<?>>>() {
            @Override
            public Option<? extends JsonDeserializer<?>> call() throws Exception {
                return option(context.findContextualValueDeserializer(referenceType, property));
            }
        });

        Option<TypeDeserializer> contextualTypeHandler = typeHandler.flatMap(new UnaryFunction<TypeDeserializer, Option<? extends TypeDeserializer>>() {
            @Override
            public Option<? extends TypeDeserializer> call(TypeDeserializer typeDeserializer) {
                return option(typeDeserializer.forProperty(property));
            }
        });

        if (contextualValueHandler.equals(valueHandler) && contextualTypeHandler.equals(typeHandler)) {
            return this;
        }

        return new OptionDeserializer(referenceType, contextualTypeHandler, contextualValueHandler);
    }
}
