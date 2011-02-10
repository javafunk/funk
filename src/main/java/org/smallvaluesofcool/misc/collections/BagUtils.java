package org.smallvaluesofcool.misc.collections;

public class BagUtils {
    public static <T> Bag<T> difference(Bag<T> bag1, Bag<T> bag2) {
        Bag<T> difference = new HashBag<T>(bag1);
        difference.removeAll(bag2);
        return difference;
    }
}
