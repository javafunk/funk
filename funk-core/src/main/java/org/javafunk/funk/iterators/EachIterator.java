package org.javafunk.funk.iterators;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javafunk.funk.functors.procedures.UnaryProcedure;

import java.util.Iterator;

import static com.google.common.base.Preconditions.checkNotNull;

public class EachIterator<T> implements Iterator<T> {
    private Iterator<T> iterator;
    private UnaryProcedure<? super T> procedure;

    public EachIterator(Iterator<T> iterator, UnaryProcedure<? super T> procedure) {
        this.iterator = checkNotNull(iterator);
        this.procedure = checkNotNull(procedure);
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        T next = iterator.next();
        procedure.execute(next);
        return next;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("iterator", iterator)
                .append("procedure", procedure)
                .toString();
    }
}
