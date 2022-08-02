package com.i4.laboratory.domain.dsl;

import org.apache.commons.lang3.RegExUtils;

public class CriteriaSupplierHelper {
    public static String toContainsText(String text) {
        return "%" + RegExUtils.replaceAll(text, "\\s", "%") + "%";
    }
}
