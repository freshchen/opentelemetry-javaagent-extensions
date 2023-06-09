package com.github.freshchen.otel.javaagent.extensions.instrumentation.servlet.v3;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * @author freshchen
 * @since 2023/3/22
 */
@AutoService(InstrumentationModule.class)
public class Servlet3InstrumentationModule extends InstrumentationModule {

    public Servlet3InstrumentationModule() {
        super("custom-servlet", "servlet-3");
    }

    @Override
    public ElementMatcher.Junction<ClassLoader> classLoaderMatcher() {
        return AgentElementMatchers.hasClassesNamed("javax.servlet.http.HttpServlet");
    }

    @Override
    public List<TypeInstrumentation> typeInstrumentations() {
        return singletonList(new Servlet3Instrumentation());
    }

    @Override
    public int order() {
        return 1;
    }
}
