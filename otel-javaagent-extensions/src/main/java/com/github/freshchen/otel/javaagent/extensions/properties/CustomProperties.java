package com.github.freshchen.otel.javaagent.extensions.properties;

import java.util.Optional;

/**
 * @author freshchen
 * @since 2023/3/22
 */
public final class CustomProperties {

    public static String readStr(String env, String prop) {
        return Optional.ofNullable(System.getenv(env))
                .orElseGet(() -> System.clearProperty(prop));
    }

    private CustomProperties() {

    }

}
