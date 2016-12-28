package com.charlesmoncada.madridguide.views;


public interface OnElementClick<T> {
    public abstract void elementClicked(T element, int position);
}
