package com.github.freshchen.otel.javaagent.extensions.trace.sampler;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.sdk.trace.samplers.Sampler;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author freshchen
 * @since 2022/10/31
 */
public class AttributeRegexSamplingRule {

    final AttributeKey<String> attributeKey;
    final Sampler delegate;
    final Pattern pattern;

    final String regexStr;

    AttributeRegexSamplingRule(AttributeKey<String> attributeKey, String pattern, Sampler delegate) {
        this.attributeKey = attributeKey;
        this.pattern = Pattern.compile(pattern);
        this.regexStr = pattern;
        this.delegate = delegate;
    }

    @Override
    public String toString() {
        return "SamplingRule{"
                + "attributeKey="
                + attributeKey
                + ", delegate="
                + delegate
                + ", pattern="
                + pattern
                + '}';
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttributeRegexSamplingRule)) {
            return false;
        }
        AttributeRegexSamplingRule that = (AttributeRegexSamplingRule) o;
        return attributeKey.equals(that.attributeKey) && pattern.equals(that.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeKey, pattern);
    }
}
