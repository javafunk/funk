package org.javafunk.funk;

import org.javafunk.funk.functors.functions.UnaryFunction;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

public class Classes {
    public static <T> T uncheckedInstantiate(final Class<T> classToInstantiate) {
        return uncheckedInstantiate(classToInstantiate, new UnaryFunction<Exception, RuntimeException>() {
            @Override public RuntimeException call(Exception exception) {
                throw new IllegalArgumentException(
                        format("Could not instantiate instance of type %s. Does it have a public no argument constructor?",
                                classToInstantiate.getSimpleName()),
                        exception);
            }
        });
    }

    public static <T> T uncheckedInstantiate(
            Class<T> classToInstantiate,
            final RuntimeException exception) {
        return uncheckedInstantiate(classToInstantiate, new UnaryFunction<Exception, RuntimeException>() {
            @Override public RuntimeException call(Exception instantiationException) {
                return exception;
            }
        });
    }

    public static <T> T uncheckedInstantiate(
            Class<T> classToInstantiate,
            UnaryFunction<? super Exception, ? extends RuntimeException> exceptionHandler) {
        checkNotNull(exceptionHandler);
        try {
            return classToInstantiate.newInstance();
        } catch (InstantiationException exception) {
            throw exceptionHandler.call(exception);
        } catch (IllegalAccessException exception) {
            throw exceptionHandler.call(exception);
        }
    }
}
