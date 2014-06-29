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
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import org.javafunk.funk.jackson.monad.OptionSerializer;
import org.javafunk.funk.monads.Option;

import static com.google.common.base.Preconditions.checkNotNull;

public class FunkSerializers extends Serializers.Base {
    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
        checkNotNull(type);
        Class<?> raw = type.getRawClass();
        if (Option.class.isAssignableFrom(raw)) {
            return new OptionSerializer(type);
        }
        return super.findSerializer(config, type, beanDesc);
    }
}
