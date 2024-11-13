package com.mini_ssafy_heaven.global.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
    servers = @Server(url = "/"),
    info = @Info(title = "미니싸피천국 API")
)
public class SwaggerConfig {

}
