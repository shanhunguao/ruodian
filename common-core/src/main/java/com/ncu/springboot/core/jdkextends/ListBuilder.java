package com.ncu.springboot.core.jdkextends;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListBuilder<V> {

    private List<V> list;

    private ListBuilder() {
    }

    public static ListBuilder getInstance() {
        return new ListBuilder();
    }

    public ListBuilder genList() {
        if (null == list) {
            list = new ArrayList<>();
        }
        return this;
    }

    public ListBuilder fromList(List<V> list) {
        this.list = list;
        return this;
    }

    public ListBuilder add(V value) {
        toList().add(value);
        return this;
    }

    public ListBuilder add(V... values) {
        List<V> list = toList();
        for (V value : values) {
            list.add(value);
        }
        return this;
    }

    public List<V> toList() {
        genList();
        return list;
    }

    public ListBuilder addAll(Collection<V> collection) {
        toList().addAll(collection);
        return this;
    }

    public ListBuilder reverse() {
        Collections.reverse(list);
        return this;
    }

    public boolean isEmpty() {
        return null == list || list.isEmpty();
    }

    public V pop() {
        if (!isEmpty()) {
            return list.remove(list.size() - 1);
        }
        return null;
    }

    public int size() {
        genList();
        return list.size();
    }

    public ListBuilder resetList() {
        list.clear();
        return this;
    }
}
