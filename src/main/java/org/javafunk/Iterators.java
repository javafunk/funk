package org.javafunk;

import org.javafunk.collections.Bag;
import org.javafunk.collections.HashBag;

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

    public static <T> Bag<T> asBag(Iterator<? extends T> iterator) {
        return new HashBag<T>(asList(iterator));
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
