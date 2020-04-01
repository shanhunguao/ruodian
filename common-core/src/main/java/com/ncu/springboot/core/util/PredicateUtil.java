package com.ncu.springboot.core.util;

import java.util.function.Predicate;

public class PredicateUtil {

    public static <T> Predicate<T> notNullPredicate() {
        return obj -> null != obj;
    }

}
