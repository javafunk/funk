/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.javafunk.funk.behaviours.ordinals.*;
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

    public static <T> Iterable<T> firsts(Iterable<? extends First<T>> firstables) {
        return Lazily.map(firstables, Tuples.<T>toFirst());
    }

    public static <T> Iterable<T> seconds(Iterable<? extends Second<T>> secondables) {
        return Lazily.map(secondables, Tuples.<T>toSecond());
    }

    public static <T> Iterable<T> thirds(Iterable<? extends Third<T>> thirdables) {
        return Lazily.map(thirdables, Tuples.<T>toThird());
    }

    public static <T> Iterable<T> fourths(Iterable<? extends Fourth<T>> fourthables) {
        return Lazily.map(fourthables, Tuples.<T>toFourth());
    }

    public static <T> Iterable<T> fifths(Iterable<? extends Fifth<T>> fifthables) {
        return Lazily.map(fifthables, Tuples.<T>toFifth());
    }

    public static <T> Iterable<T> sixths(Iterable<? extends Sixth<T>> sixthable) {
        return Lazily.map(sixthable, Tuples.<T>toSixth());
    }

    public static <T> Iterable<T> sevenths(Iterable<? extends Seventh<T>> seventhable) {
        return Lazily.map(seventhable, Tuples.<T>toSeventh());
    }

    public static <T> Iterable<T> eighths(Iterable<? extends Eighth<T>> eighthables) {
        return Lazily.map(eighthables, Tuples.<T>toEighth());
    }

    public static <T> Iterable<T> ninths(Iterable<? extends Ninth<T>> ninthables) {
        return Lazily.map(ninthables, Tuples.<T>toNinth());
    }

    public static <T> Mapper<? super First<T>, T> toFirst() {
        return new Mapper<First<T>, T>() {
            @Override public T map(First<T> firstable) {
                return firstable.getFirst();
            }
        };
    }

    public static <T> Mapper<? super Second<T>, T> toSecond() {
        return new Mapper<Second<T>, T>() {
            @Override public T map(Second<T> secondable) {
                return secondable.getSecond();
            }
        };
    }

    public static <T> Mapper<? super Third<T>, T> toThird() {
        return new Mapper<Third<T>, T>() {
            @Override public T map(Third<T> thirdables) {
                return thirdables.getThird();
            }
        };
    }

    public static <T> Mapper<? super Fourth<T>, T> toFourth() {
        return new Mapper<Fourth<T>, T>() {
            @Override public T map(Fourth<T> fourth) {
                return fourth.getFourth();
            }
        };
    }

    public static <T> Mapper<? super Fifth<T>, T> toFifth() {
        return new Mapper<Fifth<T>, T>() {
            @Override public T map(Fifth<T> fifth) {
                return fifth.getFifth();
            }
        };
    }

    public static <T> Mapper<? super Sixth<T>, T> toSixth() {
        return new Mapper<Sixth<T>, T>() {
            @Override public T map(Sixth<T> sixthable) {
                return sixthable.getSixth();
            }
        };
    }

    public static <T> Mapper<? super Seventh<T>, T> toSeventh() {
        return new Mapper<Seventh<T>, T>() {
            @Override public T map(Seventh<T> seventhable) {
                return seventhable.getSeventh();
            }
        };
    }

    public static <T> Mapper<? super Eighth<T>, T> toEighth() {
        return new Mapper<Eighth<T>, T>() {
            @Override public T map(Eighth<T> eighthable) {
                return eighthable.getEighth();
            }
        };
    }

    public static <T> Mapper<? super Ninth<T>, T> toNinth() {
        return new Mapper<Ninth<T>, T>() {
            @Override public T map(Ninth<T> ninthable) {
                return ninthable.getNinth();
            }
        };
    }
}
