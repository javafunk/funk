package org.javafunk.funk.collections;

import java.util.Collection;
import java.util.HashMap;

public class HashBag<E> extends AbstractMapBag<E> implements Bag<E> {

    public HashBag() {
        super(new HashMap<E, Integer>());
    }

    public HashBag(Collection<E> items) {
        super(new HashMap<E, Integer>(), items);
    }

}
