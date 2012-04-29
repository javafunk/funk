/*
 * Copyright (C) 2011 Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.javafunk.funk.annotations.ToDo;

import java.util.*;

public class Iterators {
    private Iterators() {}

    public static <T> Iterator<T> emptyIterator() {
        return Collections.<T>emptyList().iterator();
    }

    public static <T> Iterable<T> asIterable(Iterator<T> iterator) {
        return new IteratorAsIterable<T>(iterator);
    }

    public static <T> List<T> asList(Iterator<? extends T> iterator) {
        List<T> list = new ArrayList<T>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    public static <T> Set<T> asSet(Iterator<? extends T> iterator) {
        return new LinkedHashSet<T>(asList(iterator));
    }

    public static <T> Multiset<T> asMultiset(Iterator<? extends T> iterator) {
        return HashMultiset.create(asList(iterator));
    }

    @ToDo(raisedBy = "Toby",
          date     = "2012-04-28",
          message  = "This breaks the contract of Iterable, instead it should tee and memoize.")
    public static class IteratorAsIterable<T> implements Iterable<T> {

        private Iterator<T> iterator;

        public IteratorAsIterable(Iterator<T> iterator) {
            this.iterator = iterator;
        }

        public Iterator<T> iterator() {
            return iterator;
        }
    }
}
