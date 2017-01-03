package com.charlesmoncada.madridguide.util;


public interface OnElementClick<T> {
    public abstract void elementClicked(T element, int position);
}
