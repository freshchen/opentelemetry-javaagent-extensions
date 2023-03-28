package com.github.freshchen.otel.javaagent.extensions.common.matcher;

/**
 * @author freshchen
 * @since 2023/3/25
 */
public class EqualsStrMatcher implements StrMatcher {

    private final String target;

    public EqualsStrMatcher(String source) {
        this.target = source;
    }

    @Override
    public boolean match(String source) {
        if (target == null || source == null) {
            return false;
        }
        return source.equals(target);
    }

    @Override
    public MatcherType type() {
        return MatcherType.EQUALS;
    }
}
