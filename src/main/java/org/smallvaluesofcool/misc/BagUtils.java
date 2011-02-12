package org.smallvaluesofcool.misc;

import org.smallvaluesofcool.misc.collections.Bag;
import org.smallvaluesofcool.misc.collections.HashBag;

public class BagUtils {
    public static <T> Bag<T> difference(Bag<T> bag1, Bag<T> bag2) {
        Bag<T> difference = new HashBag<T>(bag1);
        difference.removeAll(bag2);
        return difference;
    }
}
