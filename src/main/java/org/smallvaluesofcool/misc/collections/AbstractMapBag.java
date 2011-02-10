package org.smallvaluesofcool.misc.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.smallvaluesofcool.misc.functional.Eager.sum;

public abstract class AbstractMapBag<E> implements Bag<E> {

    private Map<E, Integer> contents;

    public AbstractMapBag(Map<E, Integer> map) {
        this.contents = map;
    }

    public AbstractMapBag(HashMap<E, Integer> map, Collection items) {
        this(map);
        addAll(items);
    }

    @Override
    public boolean isEmpty() {
        return contents.isEmpty();
    }

    @Override
    public synchronized boolean add(E e) {
        Integer currentItemCount = MapUtils.getOrAddDefault(contents, e, new DefaultFactory<Integer>() {
            @Override
            public Integer create() {
                return 0;
            }
        });
        contents.put(e, currentItemCount + 1);
        return currentItemCount != 0;
    }

    @Override
    public boolean addAll(Collection<? extends E> es) {
        boolean allAdded = true;
        for (E e : es) {
            if (!add(e)) {
                allAdded = false;
            }
        }
        return allAdded;
    }

    @Override
    public boolean contains(Object o) {
        return contents.containsKey(o);
    }

    @Override
    public int size() {
        Collection<Integer> values = contents.values();
        return values.size() > 0 ? sum(values) : 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private final Iterator<Map.Entry<E, Integer>> valueIterator = contents.entrySet().iterator();
            public E value = null;
            private Integer valuesRemaining = 0;

            @Override
            public boolean hasNext() {
                return valuesRemaining >= 1 || valueIterator.hasNext();
            }

            @Override
            public E next() {
                if (valuesRemaining == 0) {
                    Map.Entry<E, Integer> nextValue = valueIterator.next();
                    value = nextValue.getKey();
                    valuesRemaining = nextValue.getValue();
                }
                valuesRemaining -= 1;
                return value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public boolean removeAll(Collection<?> es) {
        boolean allRemoved = true;
        for (Object e : es) {
            if (!remove(e)) {
                allRemoved = false;
            }
        }
        return allRemoved;
    }

    @Override
    public synchronized boolean remove(Object o) {
        Integer currentCount = contents.get(o);
        if (currentCount == null) {
            return false;
        }
        if (currentCount == 1) {
            return contents.remove(o) >= 1;
        }
        contents.put((E) o, currentCount - 1);
        return true;
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void clear() {
        contents.clear();
    }
}
