package com.mini_ssafy_heaven.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("lock")
public record LockProperties(Long waitTime, Long leaseTime) {

}
