package com.vincentcodes.jishoapi.datafilters;

/**
 * A filter that modifies its original input and returns it
 */
public interface ModificationFilter<T> {
    T filter(T input);
}
