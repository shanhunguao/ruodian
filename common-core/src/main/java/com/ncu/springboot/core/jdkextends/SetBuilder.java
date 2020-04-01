package com.ncu.springboot.core.jdkextends;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SetBuilder<V> {
    private Set<V> set;

    private SetBuilder() {
    }

    public static SetBuilder getInstance() {
        return new SetBuilder();
    }

    public SetBuilder genSet() {
        if (null == set) {
            set = new HashSet<>();
        }
        return this;
    }

    public SetBuilder add(V value) {
        toSet().add(value);
        return this;
    }

    public SetBuilder add(V... values) {
        Set<V> set = toSet();
        for(V value : values) {
            set.add(value);
        }
        return this;
    }

    public Set<V> toSet() {
        genSet();
        return set;
    }

    public SetBuilder addAll(Collection<V> collection) {
        toSet().addAll(collection);
        return this;
    }

    public boolean isEmpty() {
        return null == set || set.isEmpty();
    }

    public int size() {
        genSet();
        return set.size();
    }

    public SetBuilder resetSet() {
        set.clear();
        return this;
    }

    public SetBuilder addAll(V... valueAry) {
        Set<V> tmpSet = toSet();
        for (V value : valueAry) {
            tmpSet.add(value);
        }
        return this;
    }

}
