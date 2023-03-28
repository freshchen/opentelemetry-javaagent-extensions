package com.github.freshchen.otel.javaagent.extensions.common.matcher;

/**
 * @author freshchen
 * @since 2023/3/25
 */
public class ContainsStrMatcher implements StrMatcher {

    private final String target;

    public ContainsStrMatcher(String target) {
        this.target = target;
    }

    @Override
    public boolean match(String source) {
        if (target == null || source == null) {
            return false;
        }
        return source.contains(target);
    }

    @Override
    public MatcherType type() {
        return MatcherType.EQUALS;
    }
}
