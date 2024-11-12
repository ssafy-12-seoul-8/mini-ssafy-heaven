package com.mini_ssafy_heaven.global.interceptor;

import java.time.LocalDateTime;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

@Intercepts(
    @Signature(
        type = Executor.class,
        method = "update",
        args = {
            MappedStatement.class,
            Object.class
        }
    )
)
@Component
public class MyBatisAuditInterceptor implements Interceptor {

  private static final String CREATED_AT = "createdAt";
  private static final String UPDATED_AT = "updatedAt";

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    Object[] args = invocation.getArgs();

    if (!(args[1] instanceof MapperMethod.ParamMap<?>)) {
      return invocation.proceed();
    }

    MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
    MapperMethod.ParamMap<Object> parameter = (MapperMethod.ParamMap<Object>) args[1];

    if (isInsert(statement.getSqlCommandType())) {
      parameter.put(CREATED_AT, LocalDateTime.now());
      parameter.put(UPDATED_AT, LocalDateTime.now());
    }

    if (isUpdate(statement.getSqlCommandType())) {
      parameter.put(UPDATED_AT, LocalDateTime.now());
    }

    return invocation.proceed();
  }

  private boolean isInsert(SqlCommandType sqlCommandType) {
    return sqlCommandType == SqlCommandType.INSERT;
  }

  private boolean isUpdate(SqlCommandType sqlCommandType) {
    return sqlCommandType == SqlCommandType.UPDATE;
  }

}
