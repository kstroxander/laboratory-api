package com.i4.laboratory.config.r2dbc;

import com.i4.laboratory.domain.entities.converters.DomainEntityConverter;
import io.r2dbc.spi.ConnectionFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static java.util.Optional.of;
import static reactor.core.publisher.Mono.just;

@RequiredArgsConstructor
@Configuration
@EnableR2dbcAuditing(
        auditorAwareRef = "auditorAware",
        dateTimeProviderRef = "dateTimeProvider"
)
public class R2dbcConfiguration {
    static final String ANONYMOUS = "anonymous";

    private final ConnectionFactory connectionFactory;
    private final List<DomainEntityConverter> converters;

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        R2dbcDialect dialect = DialectResolver.getDialect(this.connectionFactory);
        return new R2dbcCustomConversions(
                CustomConversions.StoreConversions.of(dialect.getSimpleTypeHolder(), converters),
                Collections.emptyList());
    }

    @Bean
    ReactiveAuditorAware<String> auditorAware() {
        return () -> just(ANONYMOUS);
    }

    @Bean
    DateTimeProvider dateTimeProvider() {
        return () -> of(ZonedDateTime.now());
    }

    @Bean
    DSLContext dslContext() {
        return DSL.using(SQLDialect.H2, new Settings().withParamType(ParamType.NAMED));
    }

}
