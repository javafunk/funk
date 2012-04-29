package org.javafunk.funk.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ToDo {
    String raisedBy();
    String date();
    String message();
}
