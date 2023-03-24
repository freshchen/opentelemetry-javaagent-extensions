package com.github.freshchen.otel.javaagent.extensions.instrumentation.servlet.v3;

import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.namedOneOf;

/**
 * @author freshchen
 * @since 2023/3/22
 */
public class Servlet3Instrumentation implements TypeInstrumentation {

    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return AgentElementMatchers
                .hasSuperType(namedOneOf("javax.servlet.Filter", "javax.servlet.http.HttpServlet"));
    }

    @Override
    public void transform(TypeTransformer transformer) {

        transformer.applyAdviceToMethod(namedOneOf("doFilter", "service")
                        .and(ElementMatchers.takesArgument(0,
                                named("javax.servlet.ServletRequest")))
                        .and(ElementMatchers.takesArgument(1, named("javax.servlet.ServletResponse")))
                        .and(ElementMatchers.isPublic()),
                "com.github.freshchen.otel.javaagent.extensions.instrumentation.servlet.v3" +
                        ".ResponseTraceIdAdvice");
    }

    public Servlet3Instrumentation() {
    }

}
