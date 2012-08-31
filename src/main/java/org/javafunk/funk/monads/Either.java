/*
 * Copyright (C) 2011-Present Funk committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.javafunk.funk.monads;

import org.javafunk.funk.behaviours.Mappable;
import org.javafunk.funk.functors.Mapper;
import org.javafunk.funk.functors.functions.UnaryFunction;
import org.javafunk.funk.monads.eithers.Left;
import org.javafunk.funk.monads.eithers.Right;

import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.javafunk.funk.functors.adapters.MapperUnaryFunctionAdapter.mapperUnaryFunction;

/**
 * The {@code Either<L, R>} class is a base class for implementations of the either monad.
 * The either monad can be either left or right and represents the case where a value
 * can be one of two types, that of the left slot, {@code L}, or that of the right slot,
 * {@code R}. A left either is represented by the {@code Left<L, R>} subclass and a right
 * either is represented by the {@code Right<L, R>} subclass.
 *
 * <p>The {@code Either} monad is commonly used to represent the presence of either a
 * correct value or an error. By convention, the left slot is used to hold the error and
 * the right slot the value. In this way, exception handling can be replaced by
 * a returned value that can be queried for the outcome of an operation.</p>
 *
 * <p>The {@code Either} class provides factory methods for constructing {@code Either}
 * instances. {@code Either} instances provide query methods, value access methods
 * and mapping methods.</p>
 *
 * <p>{@code Either} equality is based on the equivalence of the contained value and
 * the nature of the {@code Either} whether that be {@code Left} or {@code Right}.
 * Thus {@code Either} is a value object. Unfortunately, due to type erasure,
 * {@code new Left<Integer, String>(1).equals(new Left<Integer, Long>(1))} is
 * {@code true}, i.e., for a {@code Left}, equality does not include the type
 * of the right slot. The same is true for {@code Right}.</p>
 *
 * <p>An {@code Either} is immutable but translation and mapping methods are planned
 * for a future release.</p>
 *
 * <h4>Example Usage</h4>
 *
 * Consider a service for fetching an XML feed from a remote system with the following
 * interface:
 * <blockquote>
 * <pre>
 *   public interface XmlFeedService {
 *       Either&lt;ErrorReport, XmlFeed&gt; fetchFor(Date date);
 *   }
 * </pre>
 * </blockquote>
 * A consumer of this service can query the returned {@code Either} to determine the
 * correct course of action:
 * <blockquote>
 * <pre>
 *   Either&lt;ErrorReport, XmlFeed&gt; xmlFetchEither = xmlFeedService.fetchFor(Dates.today());
 *   if(xmlFetchEither.isLeft()) {
 *       ErrorReport errorReport = xmlFetchEither.getLeft();
 *       errorRepository.store(errorReport);
 *       return yesterdaysXmlFeed;
 *   }
 *   return xmlFetchEither.getRight()
 * </pre>
 * </blockquote>
 * It can also be mapped into a new value in the case that it is a right, whilst propagating the
 * ErrorReport in the case that it is a left:
 * <blockquote>
 * <pre>
 *   Either&lt;ErrorReport, XmlFeed&gt; xmlFetchEither = xmlFeedService.fetchFor(Dates.today());
 *   Either&lt;ErrorReport, XmlNode&gt; xmlNodeEither = xmlFetchEither.map(new Mapper&lt;XmlFeed, XmlNode&gt;>(){
 *       &#64;Override public XmlNode map(XmlFeed feed) {
 *           return feed.getNode("postcode");
 *       }
 *   });
 * </pre>
 * </blockquote>
 * A potential implementation of the {@code XmlFeedService} might look like:
 * <blockquote>
 * <pre>
 *   public class HttpXmlFeedService {
 *       private final HttpClient client;
 *
 *       ...
 *
 *       public Either&lt;ErrorReport, XmlFeed&gt; fetchFor(Date date) {
 *          try {
 *              return Either.right(client.get(locationFor(date), XmlFeed.class));
 *          } catch(RemoteServiceException exception) {
 *              return Either.left(new ErrorReport(date, exception));
 *          }
 *       }
 *   }
 * </pre>
 * </blockquote>
 *
 * @param <L> The type of the value to be held in the left slot of this {@code Either}.
 * @param <R> The type of the value to be held in the right slot of this {@code Either}.
 * @see Left
 * @see Right
 * @since 1.0
 */
public abstract class Either<L, R> implements Mappable<R, Either<L, ?>> {
    /**
     * A generic factory method for building an {@code Either} over types
     * {@code L} and {@code R} representing the presence of a value of
     * type {@code L}, i.e., representing a left.
     *
     * @param value A value of type {@code L} to be used in the left slot
     *              of this {@code Either}.
     * @param <L>   The type of the left slot of this {@code Either}.
     * @param <R>   The type of the right slot of this {@code Either}.
     * @return An {@code Either<L, R>} representing a left with the
     *         supplied value.
     */
    public static <L, R> Either<L, R> left(L value) {
        return Left.left(value);
    }

    /**
     * A generic factory method for building an {@code Either} over types
     * {@code L} and {@code R} representing the presence of a value of
     * type {@code R}, i.e., representing a right.
     *
     * @param value A value of type {@code R} to be used in the right slot
     *              of this {@code Either}.
     * @param <L>   The type of the left slot of this {@code Either}.
     * @param <R>   The type of the right slot of this {@code Either}.
     * @return An {@code Either<L, R>} representing a right with the
     *         supplied value.
     */
    public static <L, R> Either<L, R> right(R value) {
        return Right.right(value);
    }

    /**
     * The no arguments constructor is protected since all sub classes should decide
     * whether or not it should be exposed. The preferred construction mechanism
     * is via the generic factory methods on {@link Either}, {@link Left} and
     * {@link Right}.
     */
    protected Either() {}

    /**
     * A query method to determine whether this {@code Either} represents
     * a value of the left type {@code L}. This method will return
     * {@code true} if this {@code Either} represents a left and
     * {@code false} if it represents a right.
     *
     * @return A {@code boolean} representing whether or not this {@code Either}
     *         is a left.
     */
    public abstract boolean isLeft();

    /**
     * A query method to determine whether this {@code Either} represents
     * a value of the right type {@code R}. This method will return {@code true}
     * if this {@code Either} represents a right and {@code false} if it
     * represents a left.
     *
     * @return A {@code boolean} representing whether or not this {@code Either}
     *         is a right.
     */
    public abstract boolean isRight();

    /**
     * A value access method to obtain the value in the right slot of this
     * {@code Either}. If this {@code Either} represents a right, the
     * contained value will be returned, otherwise a
     * {@code NoSuchElementException} will be thrown.
     *
     * @return The value contained in the right slot of this {@code Either}
     *         if one is present.
     * @throws NoSuchElementException if this {@code Either} does not represent
     *                                a right.
     */
    public R getRight() {
        throw new NoSuchElementException();
    }

    /**
     * A value access method to obtain the value in the left slot of this
     * {@code Either}. If this {@code Either} represents a left, the
     * contained value will be returned, otherwise a
     * {@code NoSuchElementException} will be thrown.
     *
     * @return The value contained in the left slot of this {@code Either}
     *         if one is present.
     * @throws NoSuchElementException if this {@code Either} does not represent
     *                                a left.
     */
    public L getLeft() {
        throw new NoSuchElementException();
    }

    /**
     * A mapping method to map this {@code Either} into an {@code Either}
     * over a right value of type {@code S} obtained by calling the
     * supplied {@code Mapper} with the current right value of this
     * {@code Either}.
     *
     * <p>In the case that this {@code Either} represents a left value, this
     * {@code Either} will be returned and the supplied {@code Mapper} will
     * not be called.</p>
     *
     * <p>The default mapping for an {@code Either} is of the right slot
     * since conventionally, when used for error handling, this slot
     * holds the correct value. Other mapping methods will be introduced
     * in future to map the left slot, either slot and to flat map the
     * {@code Either}.</p>
     *
     * <p>Currently the supplied {@code Mapper} will be called eagerly
     * in the case that this {@code Either} represents a right value
     * although this may become lazy in a future version of Funk.</p>
     *
     * <p>If the supplied {@code Mapper} is {@code null}, a
     * {@code NullPointerException} will be thrown.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * <blockquote>
     * <pre>
     *   Either&lt;Error, String&gt; content = loader.load("books/the_cat_in_the_hat.txt");
     *   Either&lt;Error, List&lt;String&gt;&gt; paragraphs = content.map(new Mapper&lt;String, List&lt;String&gt;&gt;(){
     *       &#64;Override public List&lt;String&gt; map(String content) {
     *           return listFrom(content.split("\\n\\n"));
     *       }
     *   }
     *   if(paragraphs.isLeft) {
     *       throw new FailedToLoadBookException();
     *   }
     *   return paragraphs.getRight();
     * </pre>
     * </blockquote>
     *
     * @param mapper A {@code Mapper} to map the right value of this
     *               {@code Either} into a value of type {@code S}.
     * @param <S>    The type of the right value of the resulting {@code Either}.
     * @return An {@code Either} representing a right value of type {@code S}
     *         obtained by calling the supplied {@code Mapper} with the
     *         current right value if this {@code Either} represents a right
     *         value, otherwise, this {@code Either}.
     * @throws NullPointerException if the supplied mapper is {@code null}.
     */
    public <S> Either<L, S> map(Mapper<? super R, ? extends S> mapper) {
        checkNotNull(mapper);
        return map(mapperUnaryFunction(mapper));
    }

    /**
     * A mapping method to map this {@code Either} into an {@code Either}
     * over a right value of type {@code S} obtained by calling the
     * supplied {@code UnaryFunction} with the current right value of this
     * {@code Either}.
     *
     * <p>In the case that this {@code Either} represents a left value, this
     * {@code Either} will be returned and the supplied {@code UnaryFunction}
     * will not be called.</p>
     *
     * <p>The default mapping for an {@code Either} is of the right slot
     * since conventionally, when used for error handling, this slot
     * holds the correct value. Other mapping methods will be introduced
     * in future to map the left slot, either slot and to flat map the
     * {@code Either}.</p>
     *
     * <p>Currently the supplied {@code UnaryFunction} will be called eagerly
     * in the case that this {@code Either} represents a right value
     * although this may become lazy in a future version of Funk.</p>
     *
     * <p>If the supplied {@code UnaryFunction} is {@code null}, a
     * {@code NullPointerException} will be thrown.</p>
     *
     * <h4>Example Usage:</h4>
     *
     * <blockquote>
     * <pre>
     *   Either&lt;Error, String&gt; content = loader.load("books/the_cat_in_the_hat.txt");
     *   Either&lt;Error, List&lt;String&gt;&gt; paragraphs = content.map(new UnaryFunction&lt;String, List&lt;String&gt;&gt;(){
     *       &#64;Override public List&lt;String&gt; call(String content) {
     *           return listFrom(content.split("\\n\\n"));
     *       }
     *   }
     *   if(paragraphs.isLeft) {
     *       throw new FailedToLoadBookException();
     *   }
     *   return paragraphs.getRight();
     * </pre>
     * </blockquote>
     *
     * @param function A {@code UnaryFunction} to map the right value of
     *                 this {@code Either} into a value of type {@code S}.
     * @param <S>      The type of the right value of the resulting {@code Either}.
     * @return An {@code Either} representing a right value of type {@code S}
     *         obtained by calling the supplied {@code UnaryFunction} with the
     *         current right value if this {@code Either} represents a right
     *         value, otherwise, this {@code Either}.
     * @throws NullPointerException if the supplied mapper is {@code null}.
     */
    public abstract <S> Either<L, S> map(UnaryFunction<? super R, ? extends S> function);
}
