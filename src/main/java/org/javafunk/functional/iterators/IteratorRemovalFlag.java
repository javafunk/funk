package org.javafunk.functional.iterators;

class IteratorRemovalFlag {
    boolean canRemove = false;

    boolean isEnabled() {
        return canRemove;
    }

    void enable() {
        this.canRemove = true;
    }

    void disable() {
        this.canRemove = false;
    }
}