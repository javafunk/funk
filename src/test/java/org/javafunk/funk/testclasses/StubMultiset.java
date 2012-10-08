package org.javafunk.funk.testclasses;

import com.google.common.collect.Multiset;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class StubMultiset<E> implements Multiset<E> {
    @Override public int count(Object element) { return 0; }
    @Override public int add(E element, int occurrences) { return 0; }
    @Override public int remove(Object element, int occurrences) { return 0; }
    @Override public int setCount(E element, int count) { return 0; }
    @Override public boolean setCount(E element, int oldCount, int newCount) { return false; }
    @Override public Set<E> elementSet() { return null; }
    @Override public Set<Entry<E>> entrySet() { return null; }
    @Override public Iterator<E> iterator() { return null; }
    @Override public Object[] toArray() { return new Object[0]; }
    @Override public <T> T[] toArray(T[] ts) { return null; }
    @Override public int size() { return 0; }
    @Override public boolean isEmpty() { return false; }
    @Override public boolean contains(Object element) { return false; }
    @Override public boolean containsAll(Collection<?> elements) { return false; }
    @Override public boolean addAll(Collection<? extends E> es) { return false; }
    @Override public boolean add(E element) { return false; }
    @Override public boolean remove(Object element) { return false; }
    @Override public boolean removeAll(Collection<?> c) { return false; }
    @Override public boolean retainAll(Collection<?> c) { return false; }
    @Override public void clear() { }
}
