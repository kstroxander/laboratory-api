package com.i4.laboratory.config.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.time.ZoneOffset;
import java.util.TimeZone;

@Configuration
public class ReactiveWebConfiguration implements WebFluxConfigurer {
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        ObjectMapper objMapper = Jackson2ObjectMapperBuilder.json()
                .timeZone(TimeZone.getTimeZone(ZoneOffset.UTC))
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .defaultViewInclusion(true)
                .featuresToDisable(
                        SerializationFeature.FAIL_ON_EMPTY_BEANS,
                        SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
                ).modulesToInstall(
                        new JavaTimeModule(),
                        new Jdk8Module(),
                        new ParameterNamesModule()
                )
                .build();
        configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objMapper));
        configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objMapper));
    }
}
