package com.i4.laboratory.utils;

import static java.util.Objects.nonNull;

public class ValueUtils {
    public static <T> T defaultValue(T value, T defaultValue) {
        return nonNull(value) ? value : defaultValue;
    }
}
