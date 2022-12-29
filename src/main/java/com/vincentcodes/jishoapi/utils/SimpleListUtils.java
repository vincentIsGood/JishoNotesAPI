package com.vincentcodes.jishoapi.utils;

import java.util.List;
import java.util.Optional;

public class SimpleListUtils {
    public static <T> Optional<T> findFirst(List<T> list){
        return list.size() > 0? Optional.of(list.get(0)) : Optional.empty();
    }
}
