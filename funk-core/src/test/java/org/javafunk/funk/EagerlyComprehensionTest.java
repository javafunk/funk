package org.javafunk.funk;

import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.Predicate;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.javafunk.funk.Literals.listOf;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.matchbox.Matchers.hasOnlyItemsInOrder;
import static org.junit.Assert.assertTrue;

public class EagerlyComprehensionTest {
    @Test
    public void shouldAcceptMapperSourceAndPredicate(){
        Mapper mapper = Mappers.identity();
        List<Integer> source = listWith(1,2,3);
        Predicate predicate = Predicates.alwaysTrue();
        Collection<Integer> result = Eagerly.comprehension(mapper, source, predicate);

        assertThat(result, hasOnlyItemsInOrder(1,2,3));
    }

    @Test
    public void shouldApplyPredicateToSource(){
        Mapper mapper = Mappers.identity();
        List<Integer> source = listWith(1,2,3);

        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer firstInput) {
                return firstInput % 2 != 0;
            }
        };

        Collection<Integer> result = Eagerly.comprehension(mapper, source, predicate);

        assertThat(result, hasOnlyItemsInOrder(1,3));
    }
    @Test
    public void shouldApplyMapperToPredicatedSource(){
        Mapper<Integer, Integer> mapper = new Mapper<Integer, Integer>() {
            @Override
            public Integer map(Integer input) {
                return input * 2;
            }
        };

        List<Integer> source = listWith(1,2,3);

        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer firstInput) {
                return firstInput % 2 != 0;
            }
        };

        Collection<Integer> result = Eagerly.comprehension(mapper, source, predicate);

        assertThat(result, hasOnlyItemsInOrder(2,6));
    }

    @Test
    public void shouldApplyManyPredicatesToSource(){
        Mapper<Integer, Integer> mapper = Mappers.identity();

        List<Integer> source = listWith(3,4,5,6,7,8,9);

        Predicate<Integer> oddPredicate = new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer firstInput) {
                return firstInput % 2 != 0;
            }
        };

        Predicate<Integer> divisibleByThreePredicate = new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer firstInput) {
                return firstInput % 3 == 0;
            }
        };

        Collection<Integer> result = Eagerly.comprehension(mapper, source, oddPredicate, divisibleByThreePredicate);

        assertThat(result, hasOnlyItemsInOrder(3,9));
    }

    @Test
    public void shouldApplyManyPredicatesAndMapSource(){
        Mapper<Integer, String> toUpperMapper = new Mapper<Integer, String>() {
            @Override
            public String map(Integer input) {
                return input.toString() + ": Looks good";
            }
        };

        List<Integer> source = listWith(3,4,5,6,7,8,9);

        Predicate<Integer> oddPredicate = new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer firstInput) {
                return firstInput % 2 != 0;
            }
        };

        Predicate<Integer> divisibleByThreePredicate = new Predicate<Integer>() {
            @Override
            public boolean evaluate(Integer firstInput) {
                return firstInput % 3 == 0;
            }
        };

        Collection<String> result = Eagerly.comprehension(toUpperMapper, source, oddPredicate, divisibleByThreePredicate);

        assertThat(result, hasOnlyItemsInOrder("3: Looks good", "9: Looks good"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerIfSuppliedMapperIsNull(){
        // Given
        Mapper<String, String> mapper = null;
        Iterable<String> inputs = listWith("ac", "ab", "bc", "abc", "bcd", "bad");
        Predicate<String> nonNullPredicate = Predicates.alwaysTrue();

        // When
        Eagerly.comprehension(mapper, inputs, nonNullPredicate);

        // Then a NullPointerException is thrown.
    }

    @Test
    public void shouldProvideEmptyCollectionIfSourceIterableIsEmpty(){
        //Given
        Mapper mapper = Mappers.identity();
        List<Integer> source = listOf(Integer.class);
        Predicate predicate = Predicates.alwaysTrue();

        //When
        Collection<Integer> result = Eagerly.comprehension(mapper, source, predicate);

        //Then
        assertTrue(result.isEmpty());
    }

}
