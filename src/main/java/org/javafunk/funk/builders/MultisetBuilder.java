package org.javafunk.funk.builders;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import static java.util.Arrays.asList;

public class MultisetBuilder<E> implements Multiset<E> {
    private HashMultiset<E> delegateMultiset;

    public MultisetBuilder() {
        delegateMultiset = HashMultiset.create();
    }

    public MultisetBuilder<E> with(E... elements) {
        return and(asList(elements));
    }

    public MultisetBuilder<E> with(Iterable<? extends E> elements) {
        return and(elements);
    }

    public MultisetBuilder<E> and(E... elements) {
        return and(asList(elements));
    }

    public MultisetBuilder<E> and(Iterable<? extends E> elements) {
        for (E element : elements) {
            add(element);
        }
        return this;
    }

    public Multiset<E> build() {
        return HashMultiset.create(this);
    }

    @Override
    public int count(Object element) {
        return delegateMultiset.count(element);
    }

    @Override
    public int add(E element, int occurrences) {
        return delegateMultiset.add(element, occurrences);
    }

    @Override
    public int remove(Object element, int occurrences) {
        return delegateMultiset.remove(element, occurrences);
    }

    @Override
    public int setCount(E element, int count) {
        return delegateMultiset.setCount(element, count);
    }

    @Override
    public boolean setCount(E element, int oldCount, int newCount) {
        return delegateMultiset.setCount(element, oldCount, newCount);
    }

    @Override
    public Set<E> elementSet() {
        return delegateMultiset.elementSet();
    }

    @Override
    public Set<Entry<E>> entrySet() {
        return delegateMultiset.entrySet();
    }

    @Override
    public Iterator<E> iterator() {
        return delegateMultiset.iterator();
    }

    @Override
    public Object[] toArray() {
        return delegateMultiset.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return delegateMultiset.toArray(ts);
    }

    @Override
    public int size() {
        return delegateMultiset.size();
    }

    @Override
    public boolean isEmpty() {
        return delegateMultiset.isEmpty();
    }

    @Override
    public boolean contains(Object element) {
        return delegateMultiset.contains(element);
    }

    @Override
    public boolean containsAll(Collection<?> elements) {
        return delegateMultiset.containsAll(elements);
    }

    @Override
    public boolean addAll(Collection<? extends E> es) {
        return delegateMultiset.addAll(es);
    }

    @Override
    public boolean add(E element) {
        return delegateMultiset.add(element);
    }

    @Override
    public boolean remove(Object element) {
        return delegateMultiset.remove(element);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return delegateMultiset.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return delegateMultiset.retainAll(c);
    }

    @Override
    public void clear() {
        delegateMultiset.clear();
    }

    @Override
    public boolean equals(Object o) {
        return delegateMultiset.equals(o);
    }

    @Override
    public int hashCode() {
        return delegateMultiset.hashCode();
    }

    @Override
    public String toString() {
        return delegateMultiset.toString();
    }
}
