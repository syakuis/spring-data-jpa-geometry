package io.github.syakuis.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.syakuis.configuration.support.SimpleObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class JacksonConfiguration {
    @ConditionalOnMissingBean
    @Bean
    public ObjectMapper objectMapper() {
        return SimpleObjectMapper.of(new ObjectMapper());
    }
}

