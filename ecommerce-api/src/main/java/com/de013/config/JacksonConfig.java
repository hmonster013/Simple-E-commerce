package com.de013.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.de013.custom.CustomDateDeserializer;
import com.de013.custom.CustomDateSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configurable
public class JacksonConfig {
    @Bean
    Module customDateModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new CustomDateSerializer());
        module.addDeserializer(Date.class, new CustomDateDeserializer());

        return module;
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(customDateModule());
        
        return objectMapper;
    }
}
