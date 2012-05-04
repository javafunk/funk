package org.javafunk.funk;

import org.javafunk.funk.datastructures.tuples.Pair;
import org.javafunk.funk.functors.Mapper;

import java.util.Map;

import static org.javafunk.funk.Literals.mapEntryFor;

public class Tuples {
    public static <K, V> Mapper<Pair<K, V>, Map.Entry<K, V>> toMapEntry() {
        return new Mapper<Pair<K, V>, Map.Entry<K, V>>() {
            @Override public Map.Entry<K, V> map(Pair<K, V> pair) {
                return mapEntryFor(pair);
            }
        };
    }
}
