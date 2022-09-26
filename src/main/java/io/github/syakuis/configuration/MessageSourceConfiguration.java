package io.github.syakuis.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Locale;

@RequiredArgsConstructor
@ConditionalOnBean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
@AutoConfiguration(after = MessageSource.class)
public class MessageSourceConfiguration {
    private final MessageSource messageSource;

    @ConditionalOnMissingBean
    @Bean("i18n")
    public MessageSourceAccessor i18n() {
        return new MessageSourceAccessor(messageSource, Locale.getDefault());
    }
}
