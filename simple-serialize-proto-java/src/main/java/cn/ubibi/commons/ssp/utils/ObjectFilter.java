package cn.ubibi.commons.ssp.utils;

public interface ObjectFilter<T> {
    boolean isOK(T obj);
}