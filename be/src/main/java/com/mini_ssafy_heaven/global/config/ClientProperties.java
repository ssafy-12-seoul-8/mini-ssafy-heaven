package com.mini_ssafy_heaven.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client")
public record ClientProperties(String url) {

}
