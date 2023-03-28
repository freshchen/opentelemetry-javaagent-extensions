package com.github.freshchen.otel.javaagent.extensions.common.matcher;

/**
 * @author freshchen
 * @since 2023/3/25
 */
public interface StrMatcher {

    boolean match(String source);

    MatcherType type();
}
