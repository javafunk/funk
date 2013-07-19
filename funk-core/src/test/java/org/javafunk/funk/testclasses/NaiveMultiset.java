package org.javafunk.funk.testclasses;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import org.apache.commons.lang.NotImplementedException;
import org.javafunk.funk.Eagerly;
import org.javafunk.funk.Lazily;
import org.javafunk.funk.Predicates;
import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.functors.Action;
import org.javafunk.funk.functors.Predicate;

import java.util.*;

import static org.javafunk.funk.Literals.setFrom;

public class NaiveMultiset<E> implements Multiset<E> {
    private final List<E> elements = new ArrayList<E>();

    @Override public int count(Object element) {
        return Eagerly.filter(elements, Predicates.equalTo(element)).size();
    }

    @Override public int add(final E element, int occurrences) {
        int countBefore = count(element);
        Eagerly.times(occurrences, new Action<Integer>() {
            @Override public void on(Integer index) {
                elements.add(element);
            }
        });
        return countBefore;
    }

    @Override public int remove(Object element, int occurrences) {
        int countBefore = count(element);
        Iterator<Pair<Integer, E>> matchingElements = Eagerly.filter(Lazily.enumerate(elements), new Predicate<Pair<Integer, E>>() {
            @Override public boolean evaluate(Pair<Integer, E> element) {
                return element.getSecond().equals(element);
            }
        }).iterator();

        int numberRemoved = 0;
        while(matchingElements.hasNext() && numberRemoved < occurrences) {
            Pair<Integer, E> next = matchingElements.next();
            elements.remove(next.getFirst().intValue());
            numberRemoved += 1;
        }

        return countBefore;
    }

    @Override public int setCount(E element, int count) {
        throw new NotImplementedException();
    }

    @Override public boolean setCount(E element, int oldCount, int newCount) {
        throw new NotImplementedException();
    }

    @Override public Set<E> elementSet() { return setFrom(elements); }

    @Override public Set<Entry<E>> entrySet() {
        return LinkedHashMultiset.create(elements).entrySet();
    }

    @Override public Iterator<E> iterator() { return elements.iterator(); }
    @Override public Object[] toArray() { return elements.toArray(); }
    @Override public <T> T[] toArray(T[] ts) { return elements.toArray(ts); }
    @Override public int size() { return elements.size(); }
    @Override public boolean isEmpty() { return elements.isEmpty(); }
    @Override public boolean contains(Object element) { return elements.contains(element); }
    @Override public boolean containsAll(Collection<?> elements) { return this.elements.containsAll(elements); }
    @Override public boolean addAll(Collection<? extends E> es) { return elements.addAll(es); }
    @Override public boolean add(E element) { return elements.add(element); }
    @Override public boolean remove(Object element) { return elements.remove(element); }
    @Override public boolean removeAll(Collection<?> c) { return elements.removeAll(c); }
    @Override public boolean retainAll(Collection<?> c) { return elements.retainAll(c); }
    @Override public void clear() { elements.clear(); }

    public boolean equals(Object other) {
        return elements.equals(other);
    }

    public int hashCode() {
        return elements.hashCode();
    }
}
