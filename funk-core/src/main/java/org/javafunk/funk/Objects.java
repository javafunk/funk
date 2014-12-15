/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.predicates.UnaryPredicate;

public class Objects {
    public static boolean isNull(Object value) {
        return value == null;
    }

    public static boolean isNotNull(Object value) {
        return !isNull(value);
    }

    public static UnaryPredicate<Object> whereNull() {
        return new UnaryPredicate<Object>() {
            @Override public boolean evaluate(Object input) {
                return isNull(input);
            }
        };
    }

    public static UnaryPredicate<Object> whereNotNull() {
        return new UnaryPredicate<Object>() {
            @Override public boolean evaluate(Object input) {
                return isNotNull(input);
            }
        };
    }

    public static Mapper<Object, String> toStringValue() {
        return new Mapper<Object, String>() {
            @Override public String map(Object input) {
                return input.toString();
            }
        };
    }
}
