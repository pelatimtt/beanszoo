package com.dataheaps.beanszoo.utils;


import java.util.*;

/**
 * Created by admin on 29/5/16.
 */
public class Multimap<K, V> {

    Map<K, Set<V>> root = new HashMap<>();

    synchronized public void put(K key, V value) {

        Set<V> values = root.get(key);
        if (values == null) {
            values = Collections.synchronizedSet(new HashSet<V>());
            root.put(key, values);
        }
        values.add(value)   ;

    }

    synchronized public Set<Map.Entry<K, Set<V>>> entrySet() {
        return new HashSet<>(root.entrySet());
    }

    synchronized public Set<K> keySet() {
        return new HashSet<>(root.keySet());
    }

    synchronized public void clear() {
        root.clear();
    }

    synchronized public void putAll(Map<K, V> m) {
        for (Map.Entry<K,V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    synchronized public void removeAll(Map<K, V> m) {
        for (Map.Entry<K,V> e : m.entrySet()) {
            remove(e.getKey(), e.getValue());
        }
    }

    synchronized public void removeAll(Multimap<K, V> other) {
        for (Map.Entry<K, Set<V>> e : other.root.entrySet()) {
            for (V v: e.getValue())
                remove(e.getKey(), v);
        }
    }

    synchronized public Set<V> get(K key) {
        Set<V> res = root.get(key);
        if (res == null) return Collections.emptySet();
        return res;
    }

    synchronized public void remove(K key, V value) {
        Set<V> values = root.get(key);
        if (values == null) return;
        values.remove(value);
        if (values.isEmpty())
            root.remove(key);
    }

    synchronized public void putAll(Multimap<K,V> other) {

        for (Map.Entry<K, Set<V>> e : other.root.entrySet()) {
            for (V v: e.getValue())
                put(e.getKey(), v);
        }

    }

    synchronized public void removeAll(K key) {
        root.remove(key);
    }

}