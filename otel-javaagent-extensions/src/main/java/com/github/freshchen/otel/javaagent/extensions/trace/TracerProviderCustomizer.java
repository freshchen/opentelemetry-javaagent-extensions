package com.github.freshchen.otel.javaagent.extensions.trace;

import com.github.freshchen.otel.javaagent.extensions.common.StringUtils;
import com.github.freshchen.otel.javaagent.extensions.trace.sampler.CompositeSampler;
import com.github.freshchen.otel.javaagent.extensions.trace.sampler.CompositeSamplerBuilder;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
import io.opentelemetry.sdk.autoconfigure.spi.ConfigurationException;
import io.opentelemetry.sdk.trace.SdkTracerProviderBuilder;
import io.opentelemetry.sdk.trace.samplers.Sampler;

import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.github.freshchen.otel.javaagent.extensions.common.Constants.PROP_SAMPLER_ATTR_DROP;

/**
 * @author freshchen
 * @since 2023/3/24
 */
public class TracerProviderCustomizer implements BiFunction<SdkTracerProviderBuilder, ConfigProperties,
        SdkTracerProviderBuilder> {

    private static final Logger logger = Logger.getLogger(TracerProviderCustomizer.class.getName());

    public static final TracerProviderCustomizer INSTANCE = new TracerProviderCustomizer();


    @Override
    public SdkTracerProviderBuilder apply(SdkTracerProviderBuilder sdkTracerProviderBuilder,
                                          ConfigProperties configProperties) {
        Sampler sampler = initSampler(configProperties);
        sdkTracerProviderBuilder.setSampler(sampler);
        return sdkTracerProviderBuilder;
    }

    private Sampler initSampler(ConfigProperties configProperties) {
        List<String> dropProperties = configProperties.getList(PROP_SAMPLER_ATTR_DROP);
        CompositeSamplerBuilder samplerBuilder = new CompositeSamplerBuilder(Sampler.alwaysOn());
        dropProperties.stream()
                .map(keyValuePair -> StringUtils.filterBlanksAndNulls(keyValuePair.split("=", 2)))
                .forEach(
                        splitKeyValuePairs -> {
                            if (splitKeyValuePairs.size() != 2) {
                                throw new ConfigurationException(
                                        "Invalid map property: " + PROP_SAMPLER_ATTR_DROP + "="
                                                + configProperties.getString(PROP_SAMPLER_ATTR_DROP));
                            }
                            String key = splitKeyValuePairs.get(0);
                            String value = splitKeyValuePairs.get(1);
                            samplerBuilder.drop(AttributeKey.stringKey(key), value);
                            logger.log(Level.INFO, "span with attribute {0}={1} will not be sampled",
                                    new Object[]{key, value});
                        });

        CompositeSampler customSampler = samplerBuilder.build();
        return Sampler.parentBased(customSampler);
    }
}
