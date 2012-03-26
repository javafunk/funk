package org.javafunk.funk.generators;

import org.javafunk.funk.Literals;
import org.javafunk.funk.functors.Indexer;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.matchers.SelfDescribingPredicate;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.javafunk.funk.Eager.*;
import static org.javafunk.funk.Generators.toGeneratable;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.matchers.Matchers.between;
import static org.javafunk.funk.matchers.Matchers.trueForAll;

public class RandomGeneratorTest {
    @Test
    public void shouldPickObjectsAtRandomFromTheSuppliedIterable() throws Exception {
        // Given
        Literals.ListBuilder<Integer> elements = listWith(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        RandomGenerator<Integer> generator = new RandomGenerator<Integer>(elements);

        // When
        Map<Integer, Collection<Integer>> elementGroups = group(take(toGeneratable(generator), 100000), new Indexer<Integer, Integer>() {
            @Override public Integer index(Integer element) {
                return element;
            }
        });

        Collection<Integer> groupSizes = map(elementGroups.entrySet(), new Mapper<Map.Entry<Integer, Collection<Integer>>, Integer>() {
            @Override public Integer map(Map.Entry<Integer, Collection<Integer>> group) {
                return group.getValue().size();
            }
        });

        // Then
        assertThat(groupSizes, hasSize(10));
        assertThat(groupSizes, trueForAll(new SelfDescribingPredicate<Integer>() {
            @Override public boolean evaluate(Integer element) {
                return between(9000, 11000).matches(element);
            }

            @Override public String describe() {
                return "number of occurrences between 9000 and 11000";
            }
        }));
    }

    @Test
    public void shouldBeEqualIfSuppliedIterablesAreTheSame() throws Exception {
        // Given
        RandomGenerator<Integer> firstGenerator = new RandomGenerator<Integer>(listWith(1, 2, 3, 4, 5, 6));
        RandomGenerator<Integer> secondGenerator = new RandomGenerator<Integer>(listWith(1, 2, 3, 4, 5, 6));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(true));
    }

    @Test
    public void shouldNotBeEqualIfSuppliedIterablesAreDifferent() throws Exception {
        // Given
        RandomGenerator<Integer> firstGenerator = new RandomGenerator<Integer>(listWith(1, 2, 3, 4, 5, 6));
        RandomGenerator<Integer> secondGenerator = new RandomGenerator<Integer>(listWith(7, 8, 9, 10, 11, 12));

        // When
        boolean equal = firstGenerator.equals(secondGenerator);

        // Then
        assertThat(equal, is(false));
    }
}
