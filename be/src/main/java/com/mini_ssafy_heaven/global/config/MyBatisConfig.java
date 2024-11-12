package com.mini_ssafy_heaven.global.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Configuration
@MapperScan(basePackages="com.mini_ssafy_heaven.dao", annotationClass=Repository.class)
public class MyBatisConfig {

}
