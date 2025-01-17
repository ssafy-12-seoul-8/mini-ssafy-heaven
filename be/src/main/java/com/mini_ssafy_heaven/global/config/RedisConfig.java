package com.mini_ssafy_heaven.global.config;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

  private final RedisProperties redisProperties;

  @Bean(destroyMethod = "shutdown")
  public RedissonClient redissonClient() {
    Config config = new Config();
    String address = "redis://" + redisProperties.getHost() + ":" + redisProperties.getPort();

    config.useSingleServer()
        .setAddress(address)
        .setPassword(redisProperties.getPassword());

    return Redisson.create(config);
  }

  @Bean
  public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redissonClient) {
    return new RedissonConnectionFactory(redissonClient);
  }

}
