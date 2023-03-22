package com.github.freshchen.otel.javaagent.extensions.instrumentation.servlet3;

import io.opentelemetry.api.internal.StringUtils;
import io.opentelemetry.javaagent.bootstrap.Java8BytecodeBridge;
import io.opentelemetry.javaagent.bootstrap.internal.InstrumentationConfig;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.namedOneOf;

/**
 * @author freshchen
 * @since 2023/3/22
 */
public class CustomServlet3Instrumentation implements TypeInstrumentation {

    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return AgentElementMatchers
                .hasSuperType(namedOneOf("javax.servlet.Filter", "javax.servlet.http.HttpServlet"));
    }

    @Override
    public void transform(TypeTransformer transformer) {
        String adviceName = this.getClass().getName() + "$CustomServlet3ServerHandlerAdvice";

        transformer.applyAdviceToMethod(namedOneOf("doFilter", "service")
                        .and(ElementMatchers.takesArgument(0,
                                named("javax.servlet.ServletRequest")))
                        .and(ElementMatchers.takesArgument(1, named("javax.servlet.ServletResponse")))
                        .and(ElementMatchers.isPublic()),
                adviceName);
    }

    public CustomServlet3Instrumentation() {
    }


    @SuppressWarnings("unused")
    public static class CustomServlet3ServerHandlerAdvice {

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

//
//            String grayHeader = "x-gray-tag";
//            if (!httpServletResponse.containsHeader(grayHeader)) {
//                String gray = Baggage.fromContext(Java8BytecodeBridge.currentContext())
//                        .getEntryValue(grayHeader);
//                if (!StringUtils.isNullOrEmpty(gray)) {
//                    httpServletResponse.setHeader(grayHeader, gray);
//
//                }
//            }
            }

        }
    }
}
