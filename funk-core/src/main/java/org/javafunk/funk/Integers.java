package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;

public class Integers {
    public static Mapper<Long, Integer> fromLongToInteger() {
        return new Mapper<Long, Integer>() {
            @Override public Integer map(Long input) {
                return input.intValue();
            }
        };
    }
}
