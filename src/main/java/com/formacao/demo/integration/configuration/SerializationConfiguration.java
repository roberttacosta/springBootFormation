package com.formacao.demo.integration.configuration;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SerializationConfiguration {
    @Bean
    public Encoder encoder() {
        return new JacksonEncoder();
    }

    @Bean
    public Decoder decoder() {
        return new JacksonDecoder();
    }
}
