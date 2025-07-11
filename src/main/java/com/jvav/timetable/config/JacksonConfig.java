package com.jvav.timetable.config;

import com.jvav.timetable.common.util.JacksonUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return (builder) -> {
            JacksonUtils.SERIALIZER_MAP.forEach(builder::serializerByType);
            JacksonUtils.DESERIALIZER_MAP.forEach(builder::deserializerByType);
        };
    }
}
