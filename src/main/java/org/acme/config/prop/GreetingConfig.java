package org.acme.config.prop;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "greeting")
public interface GreetingConfig {

    @WithName("message")
    String message();

}