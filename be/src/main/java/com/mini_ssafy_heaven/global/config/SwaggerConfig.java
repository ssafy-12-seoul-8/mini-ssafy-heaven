package com.mini_ssafy_heaven.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
  servers = @Server(url = "/"),
  info = @Info(title = "미니싸피천국 API"),
  security = @SecurityRequirement(name = "세션 로그인")
)
@SecurityScheme(
  name = "세션 로그인",
  type = SecuritySchemeType.APIKEY,
  in = SecuritySchemeIn.COOKIE,
  paramName = "JSESSIONID"
)
public class SwaggerConfig {

}
