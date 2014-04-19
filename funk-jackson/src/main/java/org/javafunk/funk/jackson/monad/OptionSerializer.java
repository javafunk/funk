/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */

package org.javafunk.funk.jackson.monad;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.javafunk.funk.monads.Option;

import java.io.IOException;

public class OptionSerializer extends StdSerializer<Option<?>> {

    public OptionSerializer(JavaType type) {
        super(type);
    }

    @Override
    public boolean isEmpty(Option<?> value) {
        return value == null || value.hasNoValue();
    }

    @Override
    public void serialize(Option<?> value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        if (isEmpty(value)) {
            provider.defaultSerializeNull(jsonGenerator);
        } else {
            provider.defaultSerializeValue(value.getValue(), jsonGenerator);
        }
    }

    @Override
    public void serializeWithType(Option<?> value, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        serialize(value, jgen, provider);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        JavaType typeParameter = typeHint.containedType(0);
        if (typeParameter != null) {
            visitor.getProvider().findValueSerializer(typeParameter, null).acceptJsonFormatVisitor(visitor, typeParameter);
            return;
        }
        super.acceptJsonFormatVisitor(visitor, typeHint);
    }

}
