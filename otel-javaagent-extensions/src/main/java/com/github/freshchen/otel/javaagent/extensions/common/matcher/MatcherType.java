package com.github.freshchen.otel.javaagent.extensions.common.matcher;

/**
 * @author freshchen
 * @since 2023/3/25
 */
public enum MatcherType {

    EQUALS("equals"),
    CONTAINS("contains"),
    PREFIX("prefix");

    private final String name;

    MatcherType(String name){
        this.name = name;
    }
}
