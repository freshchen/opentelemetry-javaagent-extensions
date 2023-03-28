package com.github.freshchen.otel.javaagent.extensions.common.matcher;

/**
 * @author freshchen
 * @since 2023/3/25
 */
public class PrefixStrMatcher implements StrMatcher {

    private final String target;

    public PrefixStrMatcher(String source) {
        this.target = source;
    }

    @Override
    public boolean match(String source) {
        if (target == null || source == null) {
            return false;
        }
        return source.startsWith(target);
    }

    @Override
    public MatcherType type() {
        return MatcherType.EQUALS;
    }
}
