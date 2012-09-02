/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import com.google.common.collect.Multiset;
import org.javafunk.funk.builders.*;
import org.javafunk.funk.datastructures.tuples.*;

import java.util.*;
import java.util.Arrays;

import static java.util.Arrays.asList;

public class Literals {
    private Literals() {}

    public static <E> Iterable<E> iterable() {
        return new IterableBuilder<E>().build();
    }

    public static <E> Iterable<E> iterableOf(Class<E> elementClass) {
        return new IterableBuilder<E>().build();
    }

    public static <E> Iterable<E> iterableFrom(Iterable<? extends E> elements) {
        return new IterableBuilder<E>().with(elements).build();
    }

    public static <E> Iterable<E> iterableFrom(E[] elementArray) {
        return new IterableBuilder<E>().with(elementArray).build();
    }

    public static <E> IterableBuilder<E> iterableBuilder() {
        return new IterableBuilder<E>();
    }

    public static <E> IterableBuilder<E> iterableBuilderOf(Class<E> elementClass) {
        return new IterableBuilder<E>();
    }

    public static <E> IterableBuilder<E> iterableBuilderFrom(Iterable<? extends E> elements) {
        return new IterableBuilder<E>().with(elements);
    }

    public static <E> IterableBuilder<E> iterableBuilderFrom(E[] elementArray) {
        return new IterableBuilder<E>().with(elementArray);
    }

    public static <E> Iterator<E> iterator() {
        return new IteratorBuilder<E>().build();
    }

    public static <E> Iterator<E> iteratorOf(Class<E> elementClass) {
        return new IteratorBuilder<E>().build();
    }

    public static <E> Iterator<E> iteratorFrom(Iterable<? extends E> elements) {
        return new IteratorBuilder<E>().with(elements).build();
    }

    public static <E> Iterator<E> iteratorFrom(E[] elementArray) {
        return new IteratorBuilder<E>().with(elementArray).build();
    }

    public static <E> IteratorBuilder<E> iteratorBuilder() {
        return new IteratorBuilder<E>();
    }

    public static <E> IteratorBuilder<E> iteratorBuilderOf(Class<E> elementClass) {
        return new IteratorBuilder<E>();
    }

    public static <E> IteratorBuilder<E> iteratorBuilderFrom(Iterable<? extends E> elements) {
        return new IteratorBuilder<E>().with(elements);
    }

    public static <E> IteratorBuilder<E> iteratorBuilderFrom(E[] elementArray) {
        return new IteratorBuilder<E>().with(elementArray);
    }

    public static <E> Collection<E> collection() {
        return new CollectionBuilder<E>().build();
    }

    public static <E> Collection<E> collectionOf(Class<E> elementClass) {
        return new CollectionBuilder<E>().build();
    }

    public static <E> Collection<E> collectionFrom(Iterable<? extends E> elements) {
        return new CollectionBuilder<E>().with(elements).build();
    }

    public static <E> Collection<E> collectionFrom(E[] elementArray) {
        return new CollectionBuilder<E>().with(elementArray).build();
    }

    public static <E> CollectionBuilder<E> collectionBuilder() {
        return new CollectionBuilder<E>();
    }

    public static <E> CollectionBuilder<E> collectionBuilderOf(Class<E> elementClass) {
        return new CollectionBuilder<E>();
    }

    public static <E> CollectionBuilder<E> collectionBuilderFrom(Iterable<? extends E> elements) {
        return new CollectionBuilder<E>().with(elements);
    }

    public static <E> CollectionBuilder<E> collectionBuilderFrom(E[] elementArray) {
        return new CollectionBuilder<E>().with(elementArray);
    }

    public static <E> List<E> list() {
        return new ListBuilder<E>().build();
    }

    public static <E> List<E> listOf(Class<E> elementClass) {
        return new ListBuilder<E>().build();
    }

    public static <E> List<E> listFrom(Iterable<? extends E> elements) {
        return new ListBuilder<E>().with(elements).build();
    }

    public static <E> List<E> listFrom(E[] elementArray) {
        return new ListBuilder<E>().with(elementArray).build();
    }

    public static <E> ListBuilder<E> listBuilder() {
        return new ListBuilder<E>();
    }

    public static <E> ListBuilder<E> listBuilderOf(Class<E> elementClass) {
        return new ListBuilder<E>();
    }

    public static <E> ListBuilder<E> listBuilderFrom(Iterable<? extends E> elements) {
        return new ListBuilder<E>().with(elements);
    }

    public static <E> ListBuilder<E> listBuilderFrom(E[] elementArray) {
        return new ListBuilder<E>().with(elementArray);
    }

    public static <E> Set<E> set() {
        return new SetBuilder<E>().build();
    }

    public static <E> Set<E> setOf(Class<E> elementClass) {
        return new SetBuilder<E>().build();
    }

    public static <E> Set<E> setFrom(Iterable<? extends E> elements) {
        return new SetBuilder<E>().with(elements).build();
    }

    public static <E> Set<E> setFrom(E[] elementArray) {
        return new SetBuilder<E>().with(elementArray).build();
    }

    public static <E> SetBuilder<E> setBuilder() {
        return new SetBuilder<E>();
    }

    public static <E> SetBuilder<E> setBuilderOf(Class<E> elementClass) {
        return new SetBuilder<E>();
    }

    public static <E> SetBuilder<E> setBuilderFrom(Iterable<? extends E> elements) {
        return new SetBuilder<E>().with(elements);
    }

    public static <E> SetBuilder<E> setBuilderFrom(E[] elementArray) {
        return new SetBuilder<E>().with(elementArray);
    }

    public static <E> Multiset<E> multiset() {
        return new MultisetBuilder<E>().build();
    }

    public static <E> Multiset<E> multisetOf(Class<E> elementClass) {
        return new MultisetBuilder<E>().build();
    }

    public static <E> Multiset<E> multisetFrom(Iterable<? extends E> elements) {
        return new MultisetBuilder<E>().with(elements).build();
    }

    public static <E> Multiset<E> multisetFrom(E[] elementArray) {
        return new MultisetBuilder<E>().with(elementArray).build();
    }

    public static <E> MultisetBuilder<E> multisetBuilder() {
        return new MultisetBuilder<E>();
    }

    public static <E> MultisetBuilder<E> multisetBuilderOf(Class<E> elementClass) {
        return new MultisetBuilder<E>();
    }

    public static <E> MultisetBuilder<E> multisetBuilderFrom(Iterable<? extends E> elements) {
        return new MultisetBuilder<E>().with(elements);
    }

    public static <E> MultisetBuilder<E> multisetBuilderFrom(E[] elementArray) {
        return new MultisetBuilder<E>().with(elementArray);
    }

    public static <K, V> Map<K, V> map() {
        return new MapBuilder<K, V>().build();
    }

    public static <K, V> Map<K, V> mapOf(Class<K> keyClass, Class<V> valueClass) {
        return new MapBuilder<K, V>().build();
    }

    public static <K, V> Map<K, V> mapFromEntries(Iterable<? extends Map.Entry<K, V>> elements) {
        return new MapBuilder<K, V>().with(elements).build();
    }

    public static <K, V> Map<K, V> mapFromEntries(Map.Entry<K, V>[] elementArray) {
        return new MapBuilder<K, V>().with(elementArray).build();
    }

    public static <K, V> Map<K, V> mapFromTuples(Iterable<? extends Pair<K, V>> elements) {
        return new MapBuilder<K, V>().withPairs(elements).build();
    }

    public static <K, V> Map<K, V> mapFromTuples(Pair<K, V>[] elementArray) {
        return new MapBuilder<K, V>().withPairs(elementArray).build();
    }

    public static <K, V> MapBuilder<K, V> mapBuilder() {
        return new MapBuilder<K, V>();
    }

    public static <K, V> MapBuilder<K, V> mapBuilderOf(Class<K> keyClass, Class<V> valueClass) {
        return new MapBuilder<K, V>();
    }

    public static <K, V> MapBuilder<K, V> mapBuilderFromEntries(Iterable<? extends Map.Entry<K, V>> entries) {
        return new MapBuilder<K, V>().with(entries);
    }

    public static <K, V> MapBuilder<K, V> mapBuilderFromEntries(Map.Entry<K, V>[] entries) {
        return new MapBuilder<K, V>().with(entries);
    }

    public static <K, V> MapBuilder<K, V> mapBuilderFromTuples(Iterable<? extends Pair<K, V>> entries) {
        return new MapBuilder<K, V>().withPairs(entries);
    }

    public static <K, V> MapBuilder<K, V> mapBuilderFromTuples(Pair<K, V>[] entries) {
        return new MapBuilder<K, V>().withPairs(entries);
    }

    public static <K, V> Map.Entry<K, V> mapEntryFor(K key, V value) {
        return new AbstractMap.SimpleEntry<K, V>(key, value);
    }

    public static <K, V> Map.Entry<K, V> mapEntryFor(Pair<K, V> pair) {
        return new AbstractMap.SimpleEntry<K, V>(pair.first(), pair.second());
    }

    /**
     * Returns an empty array instance over the type {@code E}.
     *
     * @param <E> The type of the elements that would be contained by this array
     *            if it contained any.
     * @return An array instance over the type {@code E} containing no elements.
     */
    @SuppressWarnings("unchecked")
    public static <E> E[] arrayOf(Class<E> elementClass) {
        return new ArrayBuilder<E>(elementClass).build();
    }

    /**
     * Returns an array instance over the type {@code E} containing all elements
     * from the supplied {@code Iterable}. The order of the elements in the resulting
     * array is determined by the order in which they are yielded from the
     * {@code Iterable}.
     *
     * <p>The supplied {@code Iterable} must contain at least one element so that
     * the type E can be correctly inferred when constructing the array. In the
     * case that the {@code Iterable} is empty, an {@code IllegalArgumentException}
     * will be thrown.</p>
     *
     * <p>The elements in the supplied {@code Iterable} must all be of the same
     * concrete type so that the type E can be inferred deterministically when
     * constructing the array. In the case that the {@code Iterable} contains
     * elements of different concrete types, an {@code IllegalArgumentException}
     * will be thrown. If an array containing multiple concrete types of some
     * supertype is required, use the {@link #arrayFrom(Iterable, Class)}
     * variant.</p>
     *
     * @param elements An {@code Iterable} of elements from which an array should be
     *                 constructed.
     * @param <E>      The type of the elements to be contained in the returned array.
     * @return An array over the type {@code E} containing all elements from the
     *         supplied {@code Iterable} in the order they are yielded.
     * @throws IllegalArgumentException if the supplied {@code Iterable} contains no
     *                                  elements or contains elements of different
     *                                  concrete types.
     */
    @SuppressWarnings("unchecked")
    public static <E> E[] arrayFrom(Iterable<E> elements) {
        return new ArrayBuilder<E>().with(elements).build();
    }

    /**
     * Returns an array instance over the type {@code E} containing all elements
     * from the supplied {@code Iterable}. The order of the elements in the resulting
     * array is determined by the order in which they are yielded from the
     * {@code Iterable}.
     *
     * <p>Unlike {@link #arrayFrom(Iterable)}, this variant accepts empty
     * {@code Iterable}s and {@code Iterable}s containing instances of different
     * concrete types and so should be used in preference of {@link #arrayFrom(Iterable)}
     * if such {@code Iterable}s are expected.</p>
     *
     * <h4>Example Usage:</h4>
     * Assume that we have the following instances:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *   Iterable&lt;Employee&gt; employees = iterableWith(partTimeEmployee, fullTimeEmployee, hourlyEmployee);
     * </pre>
     * </blockquote>
     * If we attempt to construct an array directly from the {@code Iterable}, an
     * {@code IllegalArgumentException} will be thrown:
     * <blockquote>
     * <pre>
     *   Employee[] employeeArray = Literals.arrayFrom(employees); // => IllegalArgumentException
     * </pre>
     * </blockquote>
     * However using this variant, we obtain an array of {@code Employee} instances. The following
     * two arrays are equivalent:
     * <blockquote>
     * <pre>
     *   Employee[] employeeArray = Literals.arrayFrom(employees, Employee.class);
     *   Employee[] employeeArray = new Employee[]{partTimeEmployee, fullTimeEmployee, hourlyEmployee};
     * </pre>
     * </blockquote>
     *
     * @param elements     An {@code Iterable} of elements from which an array should be
     *                     constructed.
     * @param elementClass A {@code Class} representing the required type {@code E} of
     *                     the resultant array.
     * @param <E>          The type of the elements to be contained in the returned array.
     * @return An array over the type {@code E} containing all elements from the
     *         supplied {@code Iterable} in the order they are yielded.
     */
    public static <E> E[] arrayFrom(Iterable<? extends E> elements, Class<E> elementClass) {
        return new ArrayBuilder<E>(elementClass).with(elements).build();
    }

    /**
     * Returns an array instance over the type {@code E} containing all elements
     * from the supplied array. The order of the elements in the resulting
     * array is the same as the order of elements in the supplied array.
     *
     * <p>The supplied array must contain at least one element so that the type E
     * can be correctly inferred when constructing the array to return. In the
     * case that the array is empty, an {@code IllegalArgumentException}
     * will be thrown.</p>
     *
     * <p>The elements in the supplied array must all be of the same
     * concrete type so that the type E can be inferred deterministically when
     * constructing the array. In the case that the array contains
     * elements of different concrete types, an {@code IllegalArgumentException}
     * will be thrown. If an array containing multiple concrete types of some
     * supertype is required, use the {@link #arrayFrom(Object[], Class)}
     * variant.</p>
     *
     * @param elementArray An array of elements from which an array should be
     *                     constructed.
     * @param <E>          The type of the elements to be contained in the returned array.
     * @return An array over the type {@code E} containing all elements from the
     *         supplied array in the same order as the supplied array.
     * @throws IllegalArgumentException if the supplied {@code Iterable} contains no
     *                                  elements or contains elements of different
     *                                  concrete types.
     */
    public static <E> E[] arrayFrom(E[] elementArray) {
        return new ArrayBuilder<E>().with(elementArray).build();
    }

    /**
     * Returns an array instance over the type {@code E} containing all elements
     * from the supplied array. The order of the elements in the resulting
     * array is the same as the order of elements in the supplied array.
     *
     * <p>Unlike {@link #arrayFrom(Object[])}, this variant accepts empty
     * arrays and arrays containing instances of different concrete types and
     * so should be used in preference of {@link #arrayFrom(Object[])}
     * if such arrays are expected.</p>
     *
     * <h4>Example Usage:</h4>
     * Assume that we have the following instances:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *   Employee[] employees = new Employee[]{partTimeEmployee, fullTimeEmployee, hourlyEmployee};
     * </pre>
     * </blockquote>
     * If we attempt to construct an array directly from the array, an
     * {@code IllegalArgumentException} will be thrown:
     * <blockquote>
     * <pre>
     *   Employee[] employeeArray = Literals.arrayFrom(employees); // => IllegalArgumentException
     * </pre>
     * </blockquote>
     * However using this variant, we obtain an array of {@code Employee} instances. The following
     * two arrays are equivalent:
     * <blockquote>
     * <pre>
     *   Employee[] employeeArray = Literals.arrayFrom(employees, Employee.class);
     *   Employee[] employeeArray = new Employee[]{partTimeEmployee, fullTimeEmployee, hourlyEmployee};
     * </pre>
     * </blockquote>
     *
     * @param elementArray An array of elements from which an array should be constructed.
     * @param elementClass A {@code Class} representing the required type {@code E} of
     *                     the resultant array.
     * @param <E>          The type of the elements to be contained in the returned array.
     * @return An array over the type {@code E} containing all elements from the
     *         supplied array in the same order.
     */
    public static <E> E[] arrayFrom(E[] elementArray, Class<E> elementClass) {
        return new ArrayBuilder<E>(elementClass).with(elementArray).build();
    }

    /**
     * Returns an array over the type {@code E} containing the supplied element.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e   An element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e) {
        return arrayFrom(iterableWith(e));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2) {
        return arrayFrom(iterableWith(e1, e2));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3) {
        return arrayFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4) {
        return arrayFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param e5  The fifth element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5) {
        return arrayFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param e5  The fifth element from which to construct an array.
     * @param e6  The sixth element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return arrayFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param e5  The fifth element from which to construct an array.
     * @param e6  The sixth element from which to construct an array.
     * @param e7  The seventh element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return arrayFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param e5  The fifth element from which to construct an array.
     * @param e6  The sixth element from which to construct an array.
     * @param e7  The seventh element from which to construct an array.
     * @param e8  The eighth element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return arrayFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param e5  The fifth element from which to construct an array.
     * @param e6  The sixth element from which to construct an array.
     * @param e7  The seventh element from which to construct an array.
     * @param e8  The eighth element from which to construct an array.
     * @param e9  The ninth element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return arrayFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1  The first element from which to construct an array.
     * @param e2  The second element from which to construct an array.
     * @param e3  The third element from which to construct an array.
     * @param e4  The fourth element from which to construct an array.
     * @param e5  The fifth element from which to construct an array.
     * @param e6  The sixth element from which to construct an array.
     * @param e7  The seventh element from which to construct an array.
     * @param e8  The eighth element from which to construct an array.
     * @param e9  The ninth element from which to construct an array.
     * @param e10 The tenth element from which to construct an array.
     * @param <E> The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return arrayFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an array over the type {@code E} containing the supplied elements.
     *
     * <p>The {@code arrayWith} literals are useful in cases such as when an API
     * is written to accept arrays of objects as arguments where a varargs style
     * is more appropriate.</p>
     *
     * @param e1    The first element from which to construct an array.
     * @param e2    The second element from which to construct an array.
     * @param e3    The third element from which to construct an array.
     * @param e4    The fourth element from which to construct an array.
     * @param e5    The fifth element from which to construct an array.
     * @param e6    The sixth element from which to construct an array.
     * @param e7    The seventh element from which to construct an array.
     * @param e8    The eighth element from which to construct an array.
     * @param e9    The ninth element from which to construct an array.
     * @param e10   The tenth element from which to construct an array.
     * @param e11on The remaining elements from which to construct an array.
     * @param <E>   The type of the element contained in the returned array.
     * @return An array instance over type {@code E} containing the supplied element.
     */
    public static <E> E[] arrayWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return arrayFrom(iterableBuilderWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10).and(e11on).build());
    }

    /**
     * Returns an {@code ArrayBuilder} containing no elements. When asked to
     * build an array, the element class will be inferred from the added elements
     * which means empty arrays and mixed concrete type arrays cannot be constructed.
     *
     * <h4>Example Usage:</h4>
     * An {@code ArrayBuilder} can be used to assemble an array as follows:
     * <blockquote>
     * <pre>
     *   Integer[] array = Literals.&lt;Integer&gt;arrayBuilder()
     *           .with(1, 2, 3)
     *           .and(4, 5, 6)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6}
     * </pre>
     * </blockquote>
     * The advantage of the {@code ArrayBuilder} is that the array can be built up from
     * individual objects, iterables or existing arrays. See {@link ArrayBuilder} for
     * further details.
     *
     * @param <E> The type of the elements contained in the {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over the type {@code E} containing no elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilder() {
        return new ArrayBuilder<E>();
    }

    /**
     * Returns an {@code ArrayBuilder} over the type of the supplied {@code Class}
     * containing no elements. When asked to build an array, the supplied element
     * class will be used allowing empty arrays and mixed concrete type arrays to
     * be constructed.
     *
     * <h4>Example Usage:</h4>
     * An {@code ArrayBuilder} can be used to assemble an array as follows:
     * <blockquote>
     * <pre>
     *   Integer[] array = arrayBuilderOf(Integer.class)
     *           .with(1, 2, 3)
     *           .and(4, 5, 6)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6}
     * </pre>
     * </blockquote>
     * The advantage of the {@code ArrayBuilder} is that the array can be built up from
     * individual objects, iterables or existing arrays. See {@link ArrayBuilder} for
     * further details.
     *
     * @param <E>          The type of the elements contained in the {@code ArrayBuilder}.
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code ArrayBuilder} and the type represented
     *                     by the built array.
     * @return An {@code ArrayBuilder} instance over the type {@code E} containing no elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderOf(Class<E> elementClass) {
        return new ArrayBuilder<E>(elementClass);
    }

    /**
     * Returns an {@code ArrayBuilder} over type {@code E} initialised with the elements
     * contained in the supplied {@code Iterable}. When asked to build an array, the
     * element class will be inferred from the added elements which means empty arrays
     * and mixed concrete type arrays cannot be constructed.
     *
     * <h4>Example Usage:</h4>
     * An {@code ArrayBuilder} can be used to assemble an array from two existing
     * {@code Collection} instances as follows:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstCollection = Literals.collectionWith(1, 2, 3);
     *   Collection&lt;Integer&gt; secondCollection = Literals.collectionWith(3, 4, 5);
     *   Integer[] array = arrayBuilderFrom(firstCollection)
     *           .with(secondCollection)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Integer[] array = new Integer[]{1, 2, 3, 3, 4, 5};
     * </pre>
     * </blockquote>
     * The advantage of the {@code ArrayBuilder} is that the array can be built up from
     * individual objects, iterables or existing arrays. See {@link ArrayBuilder} for
     * further details.
     *
     * @param elements An {@code Iterable} containing elements with which the
     *                 {@code ArrayBuilder} should be initialised.
     * @param <E>      The type of the elements contained in the {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over the type {@code E} containing
     *         the elements from the supplied {@code Iterable}.
     */
    public static <E> ArrayBuilder<E> arrayBuilderFrom(Iterable<E> elements) {
        return new ArrayBuilder<E>().with(elements);
    }

    /**
     * Returns an {@code ArrayBuilder} over the type of the supplied {@code Class}
     * initialised with the elements contained in the supplied {@code Iterable}.
     * When asked to build an array, the supplied element class will be used allowing
     * empty arrays and mixed concrete type arrays to be constructed.
     *
     * <h4>Example Usage:</h4>
     * An {@code ArrayBuilder} can be used to assemble an array from two existing
     * {@code Collection} instances as follows:
     * <blockquote>
     * <pre>
     *   Collection&lt;Integer&gt; firstCollection = Literals.collectionWith(1, 2, 3);
     *   Collection&lt;Integer&gt; secondCollection = Literals.collectionWith(3, 4, 5);
     *   Integer[] array = arrayBuilderFrom(firstCollection, Integer.class)
     *           .with(secondCollection)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Integer[] array = new Integer[]{1, 2, 3, 3, 4, 5};
     * </pre>
     * </blockquote>
     * The advantage of the {@code ArrayBuilder} is that the array can be built up from
     * individual objects, iterables or existing arrays. See {@link ArrayBuilder} for
     * further details.
     *
     * @param elements An {@code Iterable} containing elements with which the
     *                 {@code ArrayBuilder} should be initialised.
     * @param elementClass A {@code Class} representing the type of elements
     *                     contained in this {@code ArrayBuilder} and the type represented
     *                     by the built array.
     * @param <E>      The type of the elements contained in the {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over the type {@code E} containing
     *         the elements from the supplied {@code Iterable}.
     */
    public static <E> ArrayBuilder<E> arrayBuilderFrom(Iterable<? extends E> elements, Class<E> elementClass) {
        return new ArrayBuilder<E>(elementClass).with(elements);
    }

    /**
     * Returns an {@code ArrayBuilder} over type {@code E} initialised with the elements
     * contained in the supplied array. When asked to build an array, the element class
     * will be inferred from the added elements which means empty arrays and mixed
     * concrete type arrays cannot be constructed.
     *
     * <h4>Example Usage:</h4>
     * An {@code ArrayBuilder} can be used to assemble an array from two existing
     * arrays as follows:
     * <blockquote>
     * <pre>
     *   Integer[] firstArray = new Integer[]{1, 2, 3};
     *   Integer[] secondArray = new Integer[]{3, 4, 5};
     *   Integer[] array = arrayBuilderFrom(firstArray)
     *           .with(secondArray)
     *           .build()
     * </pre>
     * </blockquote>
     * This is equivalent to the following:
     * <blockquote>
     * <pre>
     *   Integer[] array = new Integer[]{1, 2, 3, 3, 4, 5};
     * </pre>
     * </blockquote>
     * The advantage of the {@code ArrayBuilder} is that the array can be built up from
     * individual objects, iterables or existing arrays. See {@link ArrayBuilder} for
     * further details.
     *
     * @param elementArray An array containing elements with which the {@code ArrayBuilder}
     *                     should be initialised.
     * @param <E>          The type of the elements contained in the {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over the type {@code E} containing
     *         the elements from the supplied {@code Iterable}.
     */
    public static <E> ArrayBuilder<E> arrayBuilderFrom(E[] elementArray) {
        return new ArrayBuilder<E>().with(elementArray);
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the
     * supplied element.
     *
     * @param e   The element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         element.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e) {
        return arrayBuilderFrom(iterableWith(e));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2) {
        return arrayBuilderFrom(iterableWith(e1, e2));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5  The fifth element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4, e5));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5  The fifth element to be added to the {@code ArrayBuilder}.
     * @param e6  The sixth element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5  The fifth element to be added to the {@code ArrayBuilder}.
     * @param e6  The sixth element to be added to the {@code ArrayBuilder}.
     * @param e7  The seventh element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5  The fifth element to be added to the {@code ArrayBuilder}.
     * @param e6  The sixth element to be added to the {@code ArrayBuilder}.
     * @param e7  The seventh element to be added to the {@code ArrayBuilder}.
     * @param e8  The eighth element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5  The fifth element to be added to the {@code ArrayBuilder}.
     * @param e6  The sixth element to be added to the {@code ArrayBuilder}.
     * @param e7  The seventh element to be added to the {@code ArrayBuilder}.
     * @param e8  The eighth element to be added to the {@code ArrayBuilder}.
     * @param e9  The ninth element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1  The first element to be added to the {@code ArrayBuilder}.
     * @param e2  The second element to be added to the {@code ArrayBuilder}.
     * @param e3  The third element to be added to the {@code ArrayBuilder}.
     * @param e4  The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5  The fifth element to be added to the {@code ArrayBuilder}.
     * @param e6  The sixth element to be added to the {@code ArrayBuilder}.
     * @param e7  The seventh element to be added to the {@code ArrayBuilder}.
     * @param e8  The eighth element to be added to the {@code ArrayBuilder}.
     * @param e9  The ninth element to be added to the {@code ArrayBuilder}.
     * @param e10 The tenth element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return arrayBuilderFrom(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10));
    }

    /**
     * Returns an {@code ArrayBuilder} instance over the type {@code E} containing the supplied
     * elements. The supplied elements are added to the {@code ArrayBuilder} instance in the same
     * order as they are defined in the argument list.
     *
     * <p>Note that due to type erasure, the supplied elements must all be of the same concrete
     * type otherwise the array cannot be instantiated. If an array needs to be constructed
     * from elements of different concrete types, use an {@code ArrayBuilder} directly, passing
     * in the class of the elements to be contained in the resulting array. For example:
     * <blockquote>
     * <pre>
     *   PartTimeEmployee partTimeEmployee = new PartTimeEmployee("Designer", "John");
     *   FullTimeEmployee fullTimeEmployee = new FullTimeEmployee("Manufacturer", "Fred");
     *   HourlyEmployee hourlyEmployee = new HourlyEmployee("Materials Consultant", "Andy");
     *
     *   Employee[] employees = arrayBuilderOf(Employee.class)
     *          .with(partTimeEmployee, fullTimeEmployee, hourlyEmployee)
     *          .build();
     * </pre>
     * </blockquote>
     * </p>
     *
     * @param e1    The first element to be added to the {@code ArrayBuilder}.
     * @param e2    The second element to be added to the {@code ArrayBuilder}.
     * @param e3    The third element to be added to the {@code ArrayBuilder}.
     * @param e4    The fourth element to be added to the {@code ArrayBuilder}.
     * @param e5    The fifth element to be added to the {@code ArrayBuilder}.
     * @param e6    The sixth element to be added to the {@code ArrayBuilder}.
     * @param e7    The seventh element to be added to the {@code ArrayBuilder}.
     * @param e8    The eighth element to be added to the {@code ArrayBuilder}.
     * @param e9    The ninth element to be added to the {@code ArrayBuilder}.
     * @param e10   The tenth element to be added to the {@code ArrayBuilder}.
     * @param e11on The tenth element to be added to the {@code ArrayBuilder}.
     * @param <E> The type of the elements contained in the returned {@code ArrayBuilder}.
     * @return An {@code ArrayBuilder} instance over type {@code E} containing the supplied
     *         elements.
     */
    public static <E> ArrayBuilder<E> arrayBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) {
        return arrayBuilderFrom(iterableBuilderWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10).and(e11on).build());
    }

    public static <R> Single<R> tuple(R first) {
        return new Single<R>(first);
    }

    public static <R, S> Pair<R, S> tuple(R first, S second) {
        return new Pair<R, S>(first, second);
    }

    public static <R, S, T> Triple<R, S, T> tuple(R first, S second, T third) {
        return new Triple<R, S, T>(first, second, third);
    }

    public static <R, S, T, U> Quadruple<R, S, T, U> tuple(R first, S second, T third, U fourth) {
        return new Quadruple<R, S, T, U>(first, second, third, fourth);
    }

    public static <R, S, T, U, V> Quintuple<R, S, T, U, V> tuple(R first, S second, T third, U fourth, V fifth) {
        return new Quintuple<R, S, T, U, V>(first, second, third, fourth, fifth);
    }

    public static <R, S, T, U, V, W> Sextuple<R, S, T, U, V, W> tuple(R first, S second, T third, U fourth, V fifth, W sixth) {
        return new Sextuple<R, S, T, U, V, W>(first, second, third, fourth, fifth, sixth);
    }

    public static <R, S, T, U, V, W, X> Septuple<R, S, T, U, V, W, X> tuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh) {
        return new Septuple<R, S, T, U, V, W, X>(first, second, third, fourth, fifth, sixth, seventh);
    }

    public static <R, S, T, U, V, W, X, Y> Octuple<R, S, T, U, V, W, X, Y> tuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh, Y eighth) {
        return new Octuple<R, S, T, U, V, W, X, Y>(first, second, third, fourth, fifth, sixth, seventh, eighth);
    }

    public static <R, S, T, U, V, W, X, Y, Z> Nonuple<R, S, T, U, V, W, X, Y, Z> tuple(R first, S second, T third, U fourth, V fifth, W sixth, X seventh, Y eighth, Z ninth) {
        return new Nonuple<R, S, T, U, V, W, X, Y, Z>(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth);
    }

    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e) { return iterableFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2) { return iterableFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3) { return iterableFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4) { return iterableFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5) { return iterableFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6) { return iterableFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return iterableFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> Iterable<E> iterableWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)).build(); }

    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e) { return iterableBuilderFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2) { return iterableBuilderFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3) { return iterableBuilderFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4) { return iterableBuilderFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> IterableBuilder<E> iterableBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return iterableBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)); }

    @SuppressWarnings("unchecked") public static <E> Iterator<E> iteratorWith(E e) { return iteratorFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> Iterator<E> iteratorWith(E e1, E e2) { return iteratorFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3) { return iteratorFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4) { return iteratorFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5) { return iteratorFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5, E e6) { return iteratorFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return iteratorFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return iteratorFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return iteratorFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return iteratorFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> Iterator<E> iteratorWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return iteratorBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)).build(); }

    @SuppressWarnings("unchecked") public static <E> IteratorBuilder<E> iteratorBuilderWith(E e) { return iteratorBuilderFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2) { return iteratorBuilderFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3) { return iteratorBuilderFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4) { return iteratorBuilderFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5) { return iteratorBuilderFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) { return iteratorBuilderFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return iteratorBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return iteratorBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return iteratorBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return iteratorBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> IteratorBuilder<E> iteratorBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return iteratorBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)); }

    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e) { return collectionFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2) { return collectionFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3) { return collectionFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4) { return collectionFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5) { return collectionFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6) { return collectionFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return collectionFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return collectionFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return collectionFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return collectionFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> Collection<E> collectionWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)).build(); }

    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e) { return collectionBuilderFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2) { return collectionBuilderFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3) { return collectionBuilderFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4) { return collectionBuilderFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> CollectionBuilder<E> collectionBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return collectionBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)); }

    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e) { return listFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2) { return listFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3) { return listFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4) { return listFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5) { return listFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6) { return listFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return listFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return listFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> List<E> listWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)).build(); }

    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e) { return listBuilderFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2) { return listBuilderFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3) { return listBuilderFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4) { return listBuilderFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5) { return listBuilderFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> ListBuilder<E> listBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return listBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)); }

    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e) { return setFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2) { return setFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3) { return setFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4) { return setFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5) { return setFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6) { return setFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return setFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return setFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> Set<E> setWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11on) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)).build(); }

    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e) { return setBuilderFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2) { return setBuilderFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3) { return setBuilderFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4) { return setBuilderFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5) { return setBuilderFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> SetBuilder<E> setBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11on) { return setBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)); }

    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e) { return multisetFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2) { return multisetFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3) { return multisetFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4) { return multisetFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5) { return multisetFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6) { return multisetFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return multisetFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> Multiset<E> multisetWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)).build(); }

    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e) { return multisetBuilderFrom(asList(e)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2) { return multisetBuilderFrom(asList(e1, e2)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3) { return multisetBuilderFrom(asList(e1, e2, e3)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4) { return multisetBuilderFrom(asList(e1, e2, e3, e4)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    @SuppressWarnings("unchecked") public static <E> MultisetBuilder<E> multisetBuilderWith(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E... e11on) { return multisetBuilderFrom(asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).with(asList(e11on)); }

    public static <K, V> Map<K, V> mapWith(K k1, V v1) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1)).build();
    }
    public static <K, V> Map<K, V> mapWith(K k1, V v1, K k2, V v2) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2)).build();
    }
    public static <K, V> Map<K, V> mapWith(K k1, V v1, K k2, V v2, K k3, V v3) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3)).build();
    }
    public static <K, V> Map<K, V> mapWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4)).build();
    }
    public static <K, V> Map<K, V> mapWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5)).build();
    }
    public static <K, V> Map<K, V> mapWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6)).build();
    }
    public static <K, V> Map<K, V> mapWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7)).build();
    }
    public static <K, V> Map<K, V> mapWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8)).build();
    }
    public static <K, V> Map<K, V> mapWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8), mapEntryFor(k9, v9)).build();
    }
    public static <K, V> Map<K, V> mapWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8), mapEntryFor(k9, v9), mapEntryFor(k10, v10)).build();
    }

    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1) { return mapFromEntries(iterableWith(e1)); }
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2) { return mapFromEntries(iterableWith(e1, e2)); }
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3) { return mapFromEntries(iterableWith(e1, e2, e3)); }
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4) { return mapFromEntries(iterableWith(e1, e2, e3, e4)); }
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5) { return mapFromEntries(iterableWith(e1, e2, e3, e4, e5)); }
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6) { return mapFromEntries(iterableWith(e1, e2, e3, e4, e5, e6)); }
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7) { return mapFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7)); }
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8) { return mapFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8)); }
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8, Map.Entry<K, V> e9) { return mapFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8, Map.Entry<K, V> e9, Map.Entry<K, V> e10) { return mapFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    public static <K, V> Map<K, V> mapWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8, Map.Entry<K, V> e9, Map.Entry<K, V> e10, Map.Entry<K, V>... e11on) { return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).and(e11on).build(); }

    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1) { return mapFromTuples(iterableWith(e1)); }
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2) { return mapFromTuples(iterableWith(e1, e2)); }
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3) { return mapFromTuples(iterableWith(e1, e2, e3)); }
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4) { return mapFromTuples(iterableWith(e1, e2, e3, e4)); }
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5) { return mapFromTuples(iterableWith(e1, e2, e3, e4, e5)); }
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6) { return mapFromTuples(iterableWith(e1, e2, e3, e4, e5, e6)); }
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7) { return mapFromTuples(iterableWith(e1, e2, e3, e4, e5, e6, e7)); }
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8) { return mapFromTuples(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8)); }
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8, Pair<K, V> e9) { return mapFromTuples(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8, Pair<K, V> e9, Pair<K, V> e10) { return mapFromTuples(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    public static <K, V> Map<K, V> mapWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8, Pair<K, V> e9, Pair<K, V> e10, Pair<K, V>... e11on) { return mapBuilderFromTuples(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).andPairs(e11on).build(); }

    public static <K, V> MapBuilder<K, V> mapBuilderWith(K k1, V v1) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1));
    }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(K k1, V v1, K k2, V v2) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2));
    }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(K k1, V v1, K k2, V v2, K k3, V v3) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3));
    }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4));
    }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5));
    }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6));
    }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7));
    }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8));
    }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8), mapEntryFor(k9, v9));
    }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
        return new MapBuilder<K, V>().with(mapEntryFor(k1, v1), mapEntryFor(k2, v2), mapEntryFor(k3, v3), mapEntryFor(k4, v4), mapEntryFor(k5, v5), mapEntryFor(k6, v6), mapEntryFor(k7, v7), mapEntryFor(k8, v8), mapEntryFor(k9, v9), mapEntryFor(k10, v10));
    }

    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1) { return mapBuilderFromEntries(iterableWith(e1)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2) { return mapBuilderFromEntries(iterableWith(e1, e2)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3) { return mapBuilderFromEntries(iterableWith(e1, e2, e3)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4) { return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5) { return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6) { return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7) { return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8) { return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8, Map.Entry<K, V> e9) { return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8, Map.Entry<K, V> e9, Map.Entry<K, V> e10) { return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Map.Entry<K, V> e1, Map.Entry<K, V> e2, Map.Entry<K, V> e3, Map.Entry<K, V> e4, Map.Entry<K, V> e5, Map.Entry<K, V> e6, Map.Entry<K, V> e7, Map.Entry<K, V> e8, Map.Entry<K, V> e9, Map.Entry<K, V> e10, Map.Entry<K, V>... e11on) { return mapBuilderFromEntries(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).and(e11on); }

    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1) { return mapBuilderFromTuples(iterableWith(e1)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2) { return mapBuilderFromTuples(iterableWith(e1, e2)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3) { return mapBuilderFromTuples(iterableWith(e1, e2, e3)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4) { return mapBuilderFromTuples(iterableWith(e1, e2, e3, e4)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5) { return mapBuilderFromTuples(iterableWith(e1, e2, e3, e4, e5)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6) { return mapBuilderFromTuples(iterableWith(e1, e2, e3, e4, e5, e6)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7) { return mapBuilderFromTuples(iterableWith(e1, e2, e3, e4, e5, e6, e7)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8) { return mapBuilderFromTuples(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8, Pair<K, V> e9) { return mapBuilderFromTuples(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8, Pair<K, V> e9, Pair<K, V> e10) { return mapBuilderFromTuples(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)); }
    public static <K, V> MapBuilder<K, V> mapBuilderWith(Pair<K, V> e1, Pair<K, V> e2, Pair<K, V> e3, Pair<K, V> e4, Pair<K, V> e5, Pair<K, V> e6, Pair<K, V> e7, Pair<K, V> e8, Pair<K, V> e9, Pair<K, V> e10, Pair<K, V>... e11on) { return mapBuilderFromTuples(iterableWith(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10)).andPairs(e11on); }
}
