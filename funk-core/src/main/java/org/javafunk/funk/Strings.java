/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk;

import org.apache.commons.lang3.StringUtils;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.predicates.UnaryPredicate;

import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.javafunk.funk.BigDecimals.toPlainString;
import static org.javafunk.funk.Iterables.materialize;
import static org.javafunk.funk.Literals.listWith;
import static org.javafunk.funk.Objects.toStringValue;
import static org.javafunk.funk.Objects.whereNull;
import static org.javafunk.funk.monads.Option.option;
import static org.javafunk.funk.monads.Option.some;
import static org.javafunk.funk.predicates.OrPredicate.or;

public class Strings {
    private Strings() {}

    /**
     * Joins the {@code String} representation of each of the objects
     * in the supplied {@code Iterable}, obtained by calling
     * {@code #toString}, using the supplied separator, in the
     * order in which they are yielded by the {@code Iterable}.
     *
     * <p>If the supplied {@code Iterable} is empty, the returned
     * string will also be empty. If either the {@code Iterable} or
     * the separator {@code String} are {@code null}, a
     * {@code NullPointerException} will be thrown.</p>
     *
     * <p>Any {@code null} elements in the supplied {@code Iterable}
     * are rendered as empty in the resulting {@code String}.</p>
     *
     * @param objects   The objects to be converted to their {@code String}
     *                  representation and joined using the supplied
     *                  separator.
     * @param separator A separator to be placed between each of the
     *                  {@code String} instances when joining.
     * @param <T>       The type of the objects in the {@code Iterable} to be
     *                  joined.
     * @return A {@code String} representing the concatenation of the
     *         supplied objects separated by the supplied separator.
     * @throws NullPointerException if either the supplied
     *                              {@code Iterable} or the supplied separator {@code String}
     *                              is {@code null}.
     */
    public static <T> String join(Iterable<? extends T> objects, String separator) {
        return StringUtils.join(materialize(objects), separator);
    }

    /**
     * Joins the {@code String} representation of each of the objects
     * in the supplied {@code Iterable}, obtained by calling
     * {@code #toString}, in the order in which they are yielded by
     * the {@code Iterable}.
     *
     * <p>If the supplied {@code Iterable} is empty, the returned
     * string will also be empty. If the {@code Iterable} is
     * {@code null}, a {@code NullPointerException} will be thrown.</p>
     *
     * @param objects The objects to be converted to their {@code String}
     *                representation and joined.
     * @param <T>     The type of the objects in the {@code Iterable} to be
     *                joined.
     * @return A {@code String} representing the concatenation of the
     *         supplied objects.
     * @throws NullPointerException if the supplied {@code Iterable} is
     *                              {@code null}.
     */
    public static <T> String join(Iterable<? extends T> objects) {
        return join(objects, "");
    }

    /**
     * Joins the {@code String} representation of each of the two
     * supplied objects, obtained by calling {@code #toString} on them,
     * in the order in which they are supplied.
     *
     * <p>If either of the supplied objects are {@code null}, they
     * will be rendered as empty in the returned {@code String}.</p>
     *
     * @param o1  The first object to be joined.
     * @param o2  The second object to be joined.
     * @param <T> The type of the objects to be joined.
     * @return The {@code String} concatenation of the supplied
     *         objects.
     */
    public static <T> String join(T o1, T o2) {
        return join(listWith(o1, o2));
    }

    /**
     * Joins the {@code String} representation of each of the three
     * supplied objects, obtained by calling {@code #toString} on them,
     * in the order in which they are supplied.
     *
     * <p>If any of the supplied objects are {@code null}, they
     * will be rendered as empty in the returned {@code String}.</p>
     *
     * @param o1  The first object to be joined.
     * @param o2  The second object to be joined.
     * @param o3  The third object to be joined.
     * @param <T> The type of the objects to be joined.
     * @return The {@code String} concatenation of the supplied
     *         objects.
     */
    public static <T> String join(T o1, T o2, T o3) {
        return join(listWith(o1, o2, o3));
    }

    /**
     * Joins the {@code String} representation of each of the four
     * supplied objects, obtained by calling {@code #toString} on them,
     * in the order in which they are supplied.
     *
     * <p>If any of the supplied objects are {@code null}, they
     * will be rendered as empty in the returned {@code String}.</p>
     *
     * @param o1  The first object to be joined.
     * @param o2  The second object to be joined.
     * @param o3  The third object to be joined.
     * @param o4  The fourth object to be joined.
     * @param <T> The type of the objects to be joined.
     * @return The {@code String} concatenation of the supplied
     *         objects.
     */
    public static <T> String join(T o1, T o2, T o3, T o4) {
        return join(listWith(o1, o2, o3, o4));
    }

    /**
     * Joins the {@code String} representation of each of the five
     * supplied objects, obtained by calling {@code #toString} on them,
     * in the order in which they are supplied.
     *
     * <p>If any of the supplied objects are {@code null}, they
     * will be rendered as empty in the returned {@code String}.</p>
     *
     * @param o1  The first object to be joined.
     * @param o2  The second object to be joined.
     * @param o3  The third object to be joined.
     * @param o4  The fourth object to be joined.
     * @param o5  The fifth object to be joined.
     * @param <T> The type of the objects to be joined.
     * @return The {@code String} concatenation of the supplied
     *         objects.
     */
    public static <T> String join(T o1, T o2, T o3, T o4, T o5) {
        return join(listWith(o1, o2, o3, o4, o5));
    }

    /**
     * Joins the {@code String} representation of each of the six
     * supplied objects, obtained by calling {@code #toString} on them,
     * in the order in which they are supplied.
     *
     * <p>If any of the supplied objects are {@code null}, they
     * will be rendered as empty in the returned {@code String}.</p>
     *
     * @param o1  The first object to be joined.
     * @param o2  The second object to be joined.
     * @param o3  The third object to be joined.
     * @param o4  The fourth object to be joined.
     * @param o5  The fifth object to be joined.
     * @param o6  The sixth object to be joined.
     * @param <T> The type of the objects to be joined.
     * @return The {@code String} concatenation of the supplied
     *         objects.
     */
    public static <T> String join(T o1, T o2, T o3, T o4, T o5, T o6) {
        return join(listWith(o1, o2, o3, o4, o5, o6));
    }

    /**
     * Joins the {@code String} representation of each of the seven
     * supplied objects, obtained by calling {@code #toString} on them,
     * in the order in which they are supplied.
     *
     * <p>If any of the supplied objects are {@code null}, they
     * will be rendered as empty in the returned {@code String}.</p>
     *
     * @param o1  The first object to be joined.
     * @param o2  The second object to be joined.
     * @param o3  The third object to be joined.
     * @param o4  The fourth object to be joined.
     * @param o5  The fifth object to be joined.
     * @param o6  The sixth object to be joined.
     * @param o7  The seventh object to be joined.
     * @param <T> The type of the objects to be joined.
     * @return The {@code String} concatenation of the supplied
     *         objects.
     */
    public static <T> String join(T o1, T o2, T o3, T o4, T o5, T o6, T o7) {
        return join(listWith(o1, o2, o3, o4, o5, o6, o7));
    }

    /**
     * Joins the {@code String} representation of each of the eight
     * supplied objects, obtained by calling {@code #toString} on them,
     * in the order in which they are supplied.
     *
     * <p>If any of the supplied objects are {@code null}, they
     * will be rendered as empty in the returned {@code String}.</p>
     *
     * @param o1  The first object to be joined.
     * @param o2  The second object to be joined.
     * @param o3  The third object to be joined.
     * @param o4  The fourth object to be joined.
     * @param o5  The fifth object to be joined.
     * @param o6  The sixth object to be joined.
     * @param o7  The seventh object to be joined.
     * @param o8  The eighth object to be joined.
     * @param <T> The type of the objects to be joined.
     * @return The {@code String} concatenation of the supplied
     *         objects.
     */
    public static <T> String join(T o1, T o2, T o3, T o4, T o5, T o6, T o7, T o8) {
        return join(listWith(o1, o2, o3, o4, o5, o6, o7, o8));
    }

    /**
     * Joins the {@code String} representation of each of the nine
     * supplied objects, obtained by calling {@code #toString} on them,
     * in the order in which they are supplied.
     *
     * <p>If any of the supplied objects are {@code null}, they
     * will be rendered as empty in the returned {@code String}.</p>
     *
     * @param o1  The first object to be joined.
     * @param o2  The second object to be joined.
     * @param o3  The third object to be joined.
     * @param o4  The fourth object to be joined.
     * @param o5  The fifth object to be joined.
     * @param o6  The sixth object to be joined.
     * @param o7  The seventh object to be joined.
     * @param o8  The eighth object to be joined.
     * @param o9  The ninth object to be joined.
     * @param <T> The type of the objects to be joined.
     * @return The {@code String} concatenation of the supplied
     *         objects.
     */
    public static <T> String join(T o1, T o2, T o3, T o4, T o5, T o6, T o7, T o8, T o9) {
        return join(listWith(o1, o2, o3, o4, o5, o6, o7, o8, o9));
    }

    /**
     * Joins the {@code String} representation of each of the ten
     * supplied objects, obtained by calling {@code #toString} on them,
     * in the order in which they are supplied.
     *
     * <p>If any of the supplied objects are {@code null}, they
     * will be rendered as empty in the returned {@code String}.</p>
     *
     * @param o1  The first object to be joined.
     * @param o2  The second object to be joined.
     * @param o3  The third object to be joined.
     * @param o4  The fourth object to be joined.
     * @param o5  The fifth object to be joined.
     * @param o6  The sixth object to be joined.
     * @param o7  The seventh object to be joined.
     * @param o8  The eighth object to be joined.
     * @param o9  The ninth object to be joined.
     * @param o10 The tenth object to be joined.
     * @param <T> The type of the objects to be joined.
     * @return The {@code String} concatenation of the supplied
     *         objects.
     */
    public static <T> String join(T o1, T o2, T o3, T o4, T o5, T o6, T o7, T o8, T o9, T o10) {
        return join(listWith(o1, o2, o3, o4, o5, o6, o7, o8, o9, o10));
    }

    /**
     * Joins the {@code String} representation of each of the
     * supplied objects, obtained by calling {@code #toString} on them,
     * in the order in which they are supplied.
     *
     * <p>If any of the supplied objects are {@code null}, they
     * will be rendered as empty in the returned {@code String}.</p>
     *
     * @param o1    The first object to be joined.
     * @param o2    The second object to be joined.
     * @param o3    The third object to be joined.
     * @param o4    The fourth object to be joined.
     * @param o5    The fifth object to be joined.
     * @param o6    The sixth object to be joined.
     * @param o7    The seventh object to be joined.
     * @param o8    The eighth object to be joined.
     * @param o9    The ninth object to be joined.
     * @param o10   The tenth object to be joined.
     * @param o11on The remaining objects to be joined.
     * @param <T> The type of the objects to be joined.
     * @return The {@code String} concatenation of the supplied
     *         objects.
     */
    public static <T> String join(T o1, T o2, T o3, T o4, T o5, T o6, T o7, T o8, T o9, T o10, T... o11on) {
        return join(listWith(o1, o2, o3, o4, o5, o6, o7, o8, o9, o10, o11on));
    }

    public static String toStringOrNull(Object value) {
        return toStringOr(null, value);
    }

    public static String toStringOrNull(BigDecimal value) {
        return toStringOr(null, value);
    }

    public static String toStringOrEmpty(Object value) {
        return toStringOr("", value);
    }

    public static String toStringOrEmpty(BigDecimal value) {
        return toStringOr("", value);
    }

    public static String toStringOr(String alternative, Object value) {
        return toStringOr(alternative, value, toStringValue());
    }

    public static String toStringOr(String alternative, BigDecimal value) {
        return toStringOr(alternative, value, toPlainString());
    }

    public static boolean isNullOrBlank(String value) {
        return whereNullOrBlank().evaluate(value);
    }

    public static UnaryPredicate<String> whereNullOrBlank() {
        return or(whereNull(), whereBlank());
    }

    public static UnaryPredicate<String> whereBlank() {
        return new UnaryPredicate<String>() {
            @Override public boolean evaluate(String input) {
                return isBlank(input);
            }
        };
    }

    private static <T> String toStringOr(String alternative, T value, Mapper<T, String> toStringMapper) {
        return option(value).map(toStringMapper).or(some(alternative)).get();
    }
}
