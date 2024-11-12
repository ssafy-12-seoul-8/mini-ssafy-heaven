package com.mini_ssafy_heaven.global.config;

import com.mini_ssafy_heaven.global.interceptor.MyBatisAuditInterceptor;
import java.util.Objects;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties.CoreConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan("com.mini_ssafy_heaven.dao")
@RequiredArgsConstructor
public class MyBatisConfig {

  private final DataSource dataSource;
  private final MyBatisAuditInterceptor myBatisAuditInterceptor;
  private final MybatisProperties mybatisProperties;

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    String typeAliasesPackage = mybatisProperties.getTypeAliasesPackage();
    CoreConfiguration mybatisConfig = mybatisProperties.getConfiguration();
    String mapperLocation = mybatisProperties.getMapperLocations()[0];
    Resource[] mapperLocations = new PathMatchingResourcePatternResolver()
        .getResources(mapperLocation);

    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setPlugins(myBatisAuditInterceptor);
    sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
    sqlSessionFactoryBean.setMapperLocations(mapperLocations);

    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();

    Objects.requireNonNull(sqlSessionFactory)
        .getConfiguration()
        .setMapUnderscoreToCamelCase(mybatisConfig.getMapUnderscoreToCamelCase());

    return sqlSessionFactory;
  }

}
