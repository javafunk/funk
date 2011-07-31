/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import java.util.Collection;

import static java.util.Arrays.asList;

public class Strings {
    private Strings() {}

    public static <T> String join(Collection<? extends T> collection, String separator) {
        return org.apache.commons.lang.StringUtils.join(collection, separator);
    }

    public static <T> String join(Collection<? extends T> collection) {
        return join(collection, "");
    }

    public static <T> String join(T... objects) {
        return join(asList(objects));
    }
}
