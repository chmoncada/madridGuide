package com.charlesmoncada.madridguide.model;


import java.util.List;

public interface Iterable<T> {
    long size();
    T get(long index);
    List<T> all();
}
