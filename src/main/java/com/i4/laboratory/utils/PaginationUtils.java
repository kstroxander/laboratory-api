package com.i4.laboratory.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.math.NumberUtils.createInteger;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

public class PaginationUtils {
    public static Pageable buildPageRequest(Map<String, String> queryParams){
        int page = Optional.ofNullable(queryParams.get("page"))
                .map(str -> isParsable(str) ? createInteger(str) : 1)
                .orElse(1) - 1;
        int size = Optional.ofNullable(queryParams.get("size"))
                .map(str -> isParsable(str) ? createInteger(str) : 20)
                .orElse(20);

        return PageRequest.of(Math.max(page, 0), size, Sort.by(orderBy(queryParams.get("orderBy"))));
    }

    private static List<Sort.Order> orderBy(String orderBy) {
        if(isEmpty(orderBy))
            return Collections.EMPTY_LIST;
        String[] sortParts = orderBy.split("\\|");
        return Stream.of(sortParts)
                .map(sortPart -> {
                    String[] orderByToken = sortPart.split(":");
                    String sortProperty = (orderByToken.length > 0 ? orderByToken[0] : "").trim();
                    boolean isDescendant = orderByToken.length > 1 && "desc".equalsIgnoreCase(orderByToken[1].trim());
                    return isEmpty(sortProperty) ? null : (isDescendant ? Sort.Order.desc(sortProperty) : Sort.Order.asc(sortProperty));
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
