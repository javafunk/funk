package org.javafunk.funk.builders;

import org.javafunk.funk.Eagerly;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.NullaryFunction;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.monads.Option;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.javafunk.funk.Eagerly.first;
import static org.javafunk.funk.Literals.listFrom;
import static org.javafunk.funk.Predicates.instanceOf;
import static org.javafunk.funk.Predicates.not;
import static org.javafunk.funk.monads.Option.none;
import static org.javafunk.funk.monads.Option.option;

public class ArrayBuilder<E> extends AbstractBuilder<E, ArrayBuilder<E>, E[]> {
    private List<E> elements = new ArrayList<E>();
    private Option<Class<E>> elementClassOption;

    public static <E> ArrayBuilder<E> arrayBuilder() {
        return new ArrayBuilder<E>();
    }

    public static <E> ArrayBuilder<E> arrayBuilder(Class<E> elementClass) {
        return new ArrayBuilder<E>(elementClass);
    }

    public ArrayBuilder() {
        this.elementClassOption = none();
    }

    public ArrayBuilder(Class<E> elementClass) {
        this.elementClassOption = option(elementClass);
    }

    @Override
    public E[] build() {
        return elementClassOption
                .map(toArrayUsingKnownClass(elements))
                .getOrCall(toArrayUsingInferredClass(elements));
    }

    @SuppressWarnings("unchecked")
    private NullaryFunction<E[]> toArrayUsingInferredClass(final List<E> elements) {
        return new NullaryFunction<E[]>() {
            @Override public E[] call() {
                if (elements.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Cannot construct empty array without knowing desired element class.");
                }
                Class<?> targetClass = first(elements).get().getClass();
                if (Eagerly.any(elements, not(instanceOf(targetClass)))) {
                    throw new IllegalArgumentException(
                            "Cannot construct array containing instances of different classes without knowing desired element class.");
                }
                return elements.toArray((E[]) Array.newInstance(targetClass, 0));
            }
        };
    }

    @SuppressWarnings("unchecked")
    private UnaryFunction<Class<E>, E[]> toArrayUsingKnownClass(final List<E> elements) {
        return new UnaryFunction<Class<E>, E[]>() {
            @Override public E[] call(Class<E> elementClass) {
                return listFrom(elements).toArray((E[]) Array.newInstance(elementClass, 0));
            }
        };
    }

    @Override protected void handle(E element) {
        elements.add(element);
    }

    @Override protected ArrayBuilder<E> updatedBuilder() {
        return this;
    }
}
