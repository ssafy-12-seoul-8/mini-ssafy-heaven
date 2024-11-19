package com.mini_ssafy_heaven.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("lock")
public record LockProperties(Long waitTime, Long leaseTime) {

}
