package com.github.freshchen.otel.javaagent.extensions.instrumentation.webflux.v5_0.server;

import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers.hasClassesNamed;
import static io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers.implementsInterface;
import static net.bytebuddy.matcher.ElementMatchers.isAbstract;
import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.isPublic;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;
import static net.bytebuddy.matcher.ElementMatchers.takesArgument;
import static net.bytebuddy.matcher.ElementMatchers.takesArguments;

/**
 * @author freshchen
 * @since 2023/3/23
 */
public class HandlerAdapterInstrumentation implements TypeInstrumentation {

    @Override
    public ElementMatcher<ClassLoader> classLoaderOptimization() {
        return hasClassesNamed("org.springframework.web.reactive.HandlerAdapter");
    }

    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return not(isAbstract())
                .and(implementsInterface(named("org.springframework.web.reactive.HandlerAdapter")));
    }

    @Override
    public void transform(TypeTransformer transformer) {
        transformer.applyAdviceToMethod(
                isMethod()
                        .and(isPublic())
                        .and(named("handle"))
                        .and(takesArgument(0, named("org.springframework.web.server.ServerWebExchange")))
                        .and(takesArgument(1, Object.class))
                        .and(takesArguments(2)),
                "com.github.freshchen.otel.javaagent.extensions.instrumentation.webflux.v5_0.server" +
                        ".ResponseTraceIdAdvice");
    }
}
