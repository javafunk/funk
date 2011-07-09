package org.javafunk;

import org.javafunk.collections.Bag;
import org.javafunk.collections.HashBag;

import java.util.*;

public class Iterators {
    public static <T> Iterable<T> toIterable(Iterator<T> iterator) {
        return new IteratorAsIterable<T>(iterator);
    }

    public static <T> Iterator<T> emptyIterator() {
        return Collections.<T>emptyList().iterator();
    }

    public static <T> List<T> toList(Iterator<? extends T> iterator) {
        return org.apache.commons.collections.IteratorUtils.toList(iterator);
    }

    public static <T> Set<T> toSet(Iterator<? extends T> iterator) {
        return new LinkedHashSet<T>(toList(iterator));
    }

    public static <T> Bag<T> toBag(Iterator<? extends T> iterator) {
        return new HashBag<T>(toList(iterator));
    }

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
