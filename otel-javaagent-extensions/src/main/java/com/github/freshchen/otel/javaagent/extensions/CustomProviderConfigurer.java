package com.github.freshchen.otel.javaagent.extensions;

import com.google.auto.service.AutoService;
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizer;
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizerProvider;

/**
 * @author freshchen
 * @since 2023/3/22
 */
@AutoService(AutoConfigurationCustomizerProvider.class)
public class CustomProviderConfigurer implements AutoConfigurationCustomizerProvider {

    @Override
    public void customize(AutoConfigurationCustomizer autoConfigurationCustomizer) {
        System.out.println("Opentelemetry CustomProviderConfigurer enabled");
    }
}
