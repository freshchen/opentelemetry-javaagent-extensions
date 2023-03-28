package com.github.freshchen.otel.javaagent.extensions.trace.sampler;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.context.Context;
import io.opentelemetry.sdk.trace.data.LinkData;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import io.opentelemetry.sdk.trace.samplers.SamplingResult;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author freshchen
 * @since 2022/10/31
 */
public class CompositeSampler implements Sampler {
    private final List<AttributeRegexSamplingRule> rules;
    private final Sampler fallback;

    CompositeSampler(List<AttributeRegexSamplingRule> rules, Sampler fallback) {
        this.fallback = requireNonNull(fallback);
        this.rules = requireNonNull(rules);
    }

    @Override
    public SamplingResult shouldSample(
            Context parentContext,
            String traceId,
            String name,
            SpanKind spanKind,
            Attributes attributes,
            List<LinkData> parentLinks) {
        for (AttributeRegexSamplingRule attributeRegexSamplingRule : rules) {
            String attributeValue = attributes.get(attributeRegexSamplingRule.attributeKey);
            if (attributeValue == null) {
                continue;
            }
            boolean drop = "/".equals(attributeRegexSamplingRule.regexStr) && "/".equals(attributeValue);
            if (drop) {
                return SamplingResult.drop();
            }
            boolean skip = "/".equals(attributeRegexSamplingRule.regexStr);
            if (skip) {
                continue;
            }
            boolean needHandle = attributeRegexSamplingRule.pattern.matcher(attributeValue).find();
            if (needHandle) {
                return attributeRegexSamplingRule.delegate.shouldSample(
                        parentContext, traceId, name, spanKind, attributes, parentLinks);
            }
        }
        return fallback.shouldSample(parentContext, traceId, name, spanKind, attributes, parentLinks);
    }

    @Override
    public String getDescription() {
        return "RuleBasedRoutingSampler{"
                + "rules="
                + rules
                + ", fallback="
                + fallback
                + '}';
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
