package com.github.freshchen.otel.javaagent.extensions.instrumentation.servlet.v3;

import io.opentelemetry.api.internal.StringUtils;
import io.opentelemetry.javaagent.bootstrap.Java8BytecodeBridge;
import io.opentelemetry.javaagent.bootstrap.internal.InstrumentationConfig;
import net.bytebuddy.asm.Advice;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author freshchen
 * @since 2023/3/23
 */
@SuppressWarnings("unused")
public class ResponseTraceIdAdvice {

    @Advice.OnMethodEnter(suppress = Throwable.class)
    public static void onEnter(@Advice.Argument(value = 1) ServletResponse response) {

        if (response instanceof HttpServletResponse) {
            String traceIdHeader = InstrumentationConfig.get()
                    .getString("http.response.trace.id.header");
            if (!StringUtils.isNullOrEmpty(traceIdHeader)) {
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                if (!httpServletResponse.containsHeader(traceIdHeader)) {
                    String traceId = Java8BytecodeBridge.currentSpan().getSpanContext().getTraceId();
                    if (!StringUtils.isNullOrEmpty(traceId)) {
                        httpServletResponse.setHeader(traceIdHeader, traceId);
                    }
                }
            }
        }
    }
}
