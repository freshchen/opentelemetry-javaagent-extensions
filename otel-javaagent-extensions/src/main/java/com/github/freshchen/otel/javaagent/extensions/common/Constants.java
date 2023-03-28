package com.github.freshchen.otel.javaagent.extensions.common;

/**
 * @author freshchen
 * @since 2023/3/22
 */
public final class Constants {

    /**
     * 配置 trace id 对应的响应头 name
     */
    public static final String PROP_HTTP_RESPONSE_TRACE_ID_HEADER = "http.response.trace.id.header";

    /**
     * 配置 根据 attribute 决定不采样的跨度，格式为 map
     * 例如 -Dtrace.sampler.attr.drop=http.target=/management.*,http.target=/actuator.*
     */
    public static final String PROP_SAMPLER_ATTR_DROP = "trace.sampler.attr.drop";


}
