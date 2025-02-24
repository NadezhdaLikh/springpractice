package com.itmo.springpractice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "My Spring practice", version = "1.0"))
public class OpenApi30 { // SpringDoc OpenAPI UI

}
