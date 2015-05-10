/*
 * Copyright (C) 2011-Present Funk committers.
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
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.javafunk.funk.Literals.iterableWith;
import static org.javafunk.funk.Literals.iteratorWith;
import static org.javafunk.funk.monads.Option.option;

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

    public static <T> UnaryFunction<? super Iterable<? extends T>, Iterator<? extends T>> fromIterableToIterator() {
        return new UnaryFunction<Iterable<? extends T>, Iterator<? extends T>>() {
            public Iterator<? extends T> call(Iterable<? extends T> iterable) {
                return iterable.iterator();
            }
        };
    }

    public static <T> UnaryFunction<? super Iterable<? extends T>, Iterator<? extends T>> fromIterableToIterator(Class<T> ofKlass) {
        return fromIterableToIterator();
    }

    public static <T> UnaryFunction<Iterable<? extends T>, Iterator<? extends T>> fromIterableToIteratorKeepingNull() {
        return new UnaryFunction<Iterable<? extends T>, Iterator<? extends T>>() {
            @Override public Iterator<? extends T> call(Iterable<? extends T> iterable) {
                return option(iterable).map(Iterators.<T>fromIterableToIterator()).getOrElse(Literals.<T>iteratorWith(null));
            }
        };
    }

    public static <T> UnaryFunction<Iterable<? extends T>, Iterator<? extends T>> fromIterableToIteratorKeepingNull(Class<T> ofKlass) {
        return fromIterableToIteratorKeepingNull();
    }

    @ToDo(raisedBy = "Toby",
          date     = "2012-04-28",
          message  = "This breaks the contract of Iterable, instead it should tee and memoize.")
    public static class IteratorAsIterable<T> implements Iterable<T> {
        private Iterator<T> iterator;

        public IteratorAsIterable(Iterator<T> iterator) {
            this.iterator = checkNotNull(iterator);
        }

        public Iterator<T> iterator() {
            return iterator;
        }
    }
}
