package com.i4.laboratory.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
public class DateTimeUtils {

    public static ZonedDateTime atDayStart(ZonedDateTime dateTime, ZoneId zoneId) {
        return atZone(dateTime, zoneId).truncatedTo(ChronoUnit.DAYS);
    }

    public static ZonedDateTime atMidnight(ZonedDateTime dateTime, ZoneId zoneId) {
        return atZone(dateTime, zoneId).withHour(23).withMinute(59).withSecond(59);

    }

    private static ZonedDateTime atZone(ZonedDateTime dateTime, ZoneId zoneId) {
        return dateTime.withZoneSameInstant(zoneId);
    }

    public static ZonedDateTime parseOrNull(String dateTime) {
        try {
            return isNotEmpty(dateTime) ? ZonedDateTime.parse(dateTime) : null;
        } catch (DateTimeParseException ex){
            log.error("Cannot parse Date from string '" + dateTime +"'", ex);
        }
        try {
            return isNotEmpty(dateTime) ? LocalDateTime.parse(dateTime).atZone(ZoneOffset.UTC) : null;
        } catch (DateTimeParseException ex){
            log.error("Cannot parse Date from string '" + dateTime +"'", ex);
            return null;
        }
    }
}
