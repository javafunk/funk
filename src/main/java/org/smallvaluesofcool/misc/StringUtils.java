package org.smallvaluesofcool.misc;

import java.util.Collection;

public class StringUtils {

    public static <T> String join(Collection<T> collection, String seperator) {
        return org.apache.commons.lang.StringUtils.join(collection, seperator);
    }
}
