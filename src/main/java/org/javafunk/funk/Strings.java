/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.apache.commons.lang.StringUtils;

import static java.util.Arrays.asList;
import static org.javafunk.funk.Iterables.materialize;

public class Strings {
    private Strings() {}

    public static <T> String join(Iterable<? extends T> iterable, String separator) {
        return StringUtils.join(materialize(iterable), separator);
    }

    public static <T> String join(Iterable<? extends T> collection) {
        return join(collection, "");
    }

    public static <T> String join(T... objects) {
        return join(asList(objects));
    }
}
