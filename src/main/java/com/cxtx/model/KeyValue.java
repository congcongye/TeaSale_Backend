package com.cxtx.model;

/**
 * Created by jinchuyang on 16/10/24.
 */
public interface KeyValue<K, V> {
    K getKey();
    V getValue();
}
