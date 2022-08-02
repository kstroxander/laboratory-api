package com.i4.laboratory.config.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.TimeZone;

import static com.i4.laboratory.utils.ValueUtils.defaultValue;
import static java.util.Collections.singletonList;

public class HeaderBasedLocaleContextResolver extends AcceptHeaderLocaleContextResolver {
    static final String DEFAULT_ZONE_ID ="America/Bogota";
    @Override
    public LocaleContext resolveLocaleContext(ServerWebExchange exchange) {
        LocaleContext superContext = super.resolveLocaleContext(exchange);
        ZoneId zoneId = defaultValue(exchange.getRequest().getHeaders().get("X-Timezone-Id"), singletonList(DEFAULT_ZONE_ID))
                .stream().findFirst()
                .map(String::valueOf)
                .map(ZoneId::of)
                .orElse(ZoneId.of(DEFAULT_ZONE_ID));

        return new ZoneIdLocaleContext(superContext.getLocale(), TimeZone.getTimeZone(zoneId));
    }

    @Getter
    @AllArgsConstructor
    public static class ZoneIdLocaleContext implements TimeZoneAwareLocaleContext {
        final Locale locale;
        final TimeZone timeZone;
    }
}
