package com.rancard.ussdapp.model.payload;

import java.util.HashMap;
import java.util.Map;

public class GenericValueMap<T> {

    private Map<Integer, T> integerTMap;

    public GenericValueMap() {
        integerTMap = new HashMap<>();
    }

    public void put(Integer key, T value) {
        integerTMap.put(key, (T) value);
    }

    public T get(Integer key) {
        return integerTMap.get(key);
    }

    public <K, V> int size() {
        return integerTMap.size();
    }

    public void put(int counter, String content) {
        integerTMap.put(counter , (T) content);
    }
}
