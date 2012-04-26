package org.javafunk.funk.predicates;

import org.javafunk.funk.functors.Predicate;

public enum Predicates {
    TRUE(new Predicate<Object>() {
        @Override
        public boolean evaluate(Object item) {
            return true;
        }
    }),
    FALSE(new Predicate<Object>() {
        @Override
        public boolean evaluate(Object item) {
            return false;
        }
    });

    private final Predicate predicate;

    Predicates(Predicate predicate) {
        this.predicate = predicate;
    }

    public Predicate<Object> predicate() {
        return predicate;
    }
}
