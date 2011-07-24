package org.javafunk.funk;

import com.google.common.collect.Multiset;

public class Multisets {
    private Multisets() {}

    public static <T> Multiset<T> difference(Multiset<T> firstSet, Multiset<T> secondSet) {
        Multiset<T> differences = com.google.common.collect.HashMultiset.create(firstSet);
        for (T item : secondSet) {
            differences.remove(item);
        }
        return differences;
    }
}
