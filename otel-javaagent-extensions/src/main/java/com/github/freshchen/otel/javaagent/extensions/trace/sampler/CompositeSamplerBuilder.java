package com.github.freshchen.otel.javaagent.extensions.trace.sampler;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.sdk.trace.samplers.Sampler;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author freshchen
 * @since 2022/10/31
 */
public final class CompositeSamplerBuilder {
    private final List<AttributeRegexSamplingRule> rules = new ArrayList<>();
    private final Sampler defaultDelegate;

    public CompositeSamplerBuilder(Sampler defaultDelegate) {
        this.defaultDelegate = defaultDelegate;
    }

    @CanIgnoreReturnValue
    public CompositeSamplerBuilder drop(AttributeKey<String> attributeKey, String pattern) {
        rules.add(
                new AttributeRegexSamplingRule(
                        requireNonNull(attributeKey, "attributeKey must not be null"),
                        requireNonNull(pattern, "pattern must not be null"),
                        Sampler.alwaysOff()));
        return this;
    }

    @CanIgnoreReturnValue
    public CompositeSamplerBuilder recordAndSample(AttributeKey<String> attributeKey, String pattern) {
        rules.add(
                new AttributeRegexSamplingRule(
                        requireNonNull(attributeKey, "attributeKey must not be null"),
                        requireNonNull(pattern, "pattern must not be null"),
                        Sampler.alwaysOn()));
        return this;
    }

    public CompositeSampler build() {
        return new CompositeSampler(rules, defaultDelegate);
    }
}
