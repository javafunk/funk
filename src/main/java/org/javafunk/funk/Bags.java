package org.javafunk.funk;

import org.javafunk.funk.collections.Bag;
import org.javafunk.funk.collections.HashBag;

public class Bags {
    private Bags() {}

    public static <T> Bag<T> difference(Bag<T> bag1, Bag<T> bag2) {
        Bag<T> difference = new HashBag<T>(bag1);
        difference.removeAll(bag2);
        return difference;
    }
}
