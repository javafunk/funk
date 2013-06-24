package org.javafunk.funk.iterators;

import org.javafunk.funk.Eagerly;
import org.javafunk.funk.functors.Predicate;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.functors.predicates.UnaryPredicate;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;

public class ComprehensionIterator<S, T> extends CachingIterator<T> {
    private UnaryFunction<? super S, T> mapper;
    private Iterator<? extends S> iterator;
    private Iterable<? extends UnaryPredicate<? super S>> predicates;

    public ComprehensionIterator(
            UnaryFunction<? super S, T> mapper,
            Iterator<S> iterator,
            List<? extends UnaryPredicate<? super S>> predicates) {
        this.mapper = checkNotNull(mapper);
        this.iterator = iterator;
        this.predicates = checkContainsNoNulls(predicates);
    }

    @Override
    protected T findNext() {
        while (iterator.hasNext()) {
            final S next = iterator.next();
            Boolean passesAllPredicates = Eagerly.all(
                    predicates,
                    new UnaryPredicate<UnaryPredicate<? super S>>() {
                        @Override
                        public boolean evaluate(UnaryPredicate<? super S> predicate) {
                            return predicate.evaluate(next);
                        }
                    });
            if (passesAllPredicates) {
                return mapper.call(next);
            }

        }
        throw new NoSuchElementException();
    }

    @Override
    protected void removeLast() {
        iterator.remove();
    }

    public static <S> List<? extends UnaryPredicate<? super S>> checkContainsNoNulls(
            List<? extends UnaryPredicate<? super S>> predicates) {
        Boolean anyNulls = Eagerly.any(predicates, new Predicate<UnaryPredicate<? super S>>() {
            @Override
            public boolean evaluate(UnaryPredicate<? super S> predicate) {
                return predicate == null;
            }
        });
        if (anyNulls) {throw new NullPointerException();}
        return predicates;
    }
}
