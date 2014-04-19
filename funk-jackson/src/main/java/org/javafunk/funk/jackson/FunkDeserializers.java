/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */

package org.javafunk.funk.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import org.javafunk.funk.jackson.monad.OptionDeserializer;
import org.javafunk.funk.monads.Option;

import static org.javafunk.funk.monads.Option.option;

public class FunkDeserializers extends Deserializers.Base {
    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        Class<?> raw = type.getRawClass();
        if (Option.class.isAssignableFrom(raw)) {
            TypeDeserializer typeHandler = type.getTypeHandler();
            JsonDeserializer<?> valueHandler = type.getValueHandler();
            return new OptionDeserializer(type, option(typeHandler), Option.<JsonDeserializer<?>>option(valueHandler));
        }
        return super.findBeanDeserializer(type, config, beanDesc);
    }
}
