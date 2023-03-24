package com.github.freshchen.otel.javaagent.extensions.instrumentation.servlet.v3;

import io.opentelemetry.api.baggage.Baggage;
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
public class ResponseGrayAdvice {

    @Advice.OnMethodEnter(suppress = Throwable.class)
    public static void onEnter(@Advice.Argument(value = 1) ServletResponse response) {

        if (response instanceof HttpServletResponse) {
            String grayTagHeader = InstrumentationConfig.get()
                    .getString("http.response.gray.tag.header");
            if (!StringUtils.isNullOrEmpty(grayTagHeader)) {
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                if (!httpServletResponse.containsHeader(grayTagHeader)) {
                    String grayTag = Baggage.fromContext(Java8BytecodeBridge.currentContext())
                            .getEntryValue(grayTagHeader);
                    if (!StringUtils.isNullOrEmpty(grayTag)) {
                        httpServletResponse.setHeader(grayTagHeader, grayTag);
                    }
                }
            }
        }
    }
}
