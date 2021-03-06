package com.charlesmoncada.madridguide.model;


public interface Updatable<T> {
    void add(T element);
    void delete(T element);
    void edit(T newElement, long index);
}
