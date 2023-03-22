package com.github.freshchen.otel.javaagent.extensions;

import com.google.auto.service.AutoService;
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizer;
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizerProvider;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 * @author freshchen
 * @since 2023/3/22
 */
@AutoService(AutoConfigurationCustomizerProvider.class)
public class CustomProviderConfigurer implements AutoConfigurationCustomizerProvider {

    private static final Logger logger = Logger.getLogger(CustomProviderConfigurer.class.getName());

    @Override
    public void customize(AutoConfigurationCustomizer autoConfigurationCustomizer) {
        logger.log(INFO, "Opentelemetry CustomProviderConfigurer enabled");
    }
}
