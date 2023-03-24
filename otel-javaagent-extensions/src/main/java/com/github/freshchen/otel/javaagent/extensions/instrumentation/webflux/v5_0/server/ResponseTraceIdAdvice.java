package com.github.freshchen.otel.javaagent.extensions.instrumentation.webflux.v5_0.server;

import io.opentelemetry.api.internal.StringUtils;
import io.opentelemetry.javaagent.bootstrap.Java8BytecodeBridge;
import io.opentelemetry.javaagent.bootstrap.internal.InstrumentationConfig;
import net.bytebuddy.asm.Advice;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author freshchen
 * @since 2023/3/23
 */
@SuppressWarnings("unused")
public class ResponseTraceIdAdvice {

    @Advice.OnMethodEnter(suppress = Throwable.class)
    public static void methodEnter(
            @Advice.Argument(0) ServerWebExchange exchange) {

        String traceIdHeader = InstrumentationConfig.get()
                .getString("http.response.trace.id.header");
        if (!StringUtils.isNullOrEmpty(traceIdHeader)) {
            HttpHeaders headers = exchange.getResponse().getHeaders();
            if (!headers.containsKey(traceIdHeader)) {
                String traceId = Java8BytecodeBridge.currentSpan().getSpanContext().getTraceId();
                if (!StringUtils.isNullOrEmpty(traceId)) {
                    headers.add(traceIdHeader, traceId);
                }
            }
        }
    }
}
