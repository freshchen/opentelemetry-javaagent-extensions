package com.github.freshchen.otel.javaagent.extensions.instrumentation.webflux.v5_0.server;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;

import java.util.Collections;
import java.util.List;

/**
 * @author freshchen
 * @since 2023/3/23
 */
@AutoService(InstrumentationModule.class)
public class WebfluxServerInstrumentationModule extends InstrumentationModule {

    public WebfluxServerInstrumentationModule() {
        super("custom-spring-webflux", "spring-webflux-5.0",
                "spring-webflux-server");
    }

    @Override
    public List<TypeInstrumentation> typeInstrumentations() {
        return Collections.singletonList(new HandlerAdapterInstrumentation());
    }

    @Override
    public int order() {
        return 1;
    }
}