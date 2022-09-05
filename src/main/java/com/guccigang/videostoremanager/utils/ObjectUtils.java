package com.guccigang.videostoremanager.utils;

abstract class ObjectUtils<T> {
    public abstract T parse(String str);

    public abstract String serialize(T obj);
}