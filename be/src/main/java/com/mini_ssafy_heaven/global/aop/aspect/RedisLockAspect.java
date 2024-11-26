package com.mini_ssafy_heaven.global.aop.aspect;

import com.mini_ssafy_heaven.global.annotation.Lock;
import com.mini_ssafy_heaven.global.config.LockProperties;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisLockAspect {

  private final RedissonClient redissonClient;
  private final LockProperties lockProperties;

  @Around("@annotation(com.mini_ssafy_heaven.global.annotation.Lock) && args(id, ..)")
  public Object lock(ProceedingJoinPoint joinPoint, Long id) throws Throwable {
    Lock lockAnnotation = getAnnotation(joinPoint);
    String key = lockAnnotation.value() + ":" + id;
    Long waitTime =
        lockAnnotation.waitTime() == 0 ? lockProperties.waitTime() : lockAnnotation.waitTime();
    Long leaseTime =
        lockAnnotation.leaseTime() == 0 ? lockProperties.leaseTime() : lockAnnotation.leaseTime();
    RLock lock = redissonClient.getLock(key);

    try {
      boolean locked = lock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);

      log.debug("locked : {}", lock);

      return doLock(locked, key, joinPoint);
    } catch (InterruptedException e) {
      log.debug("error occurred", e);

      throw new RuntimeException(e);
    } finally {
      lock.unlock();
    }
  }

  private Lock getAnnotation(ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    return method.getAnnotation(Lock.class);
  }

  private Object doLock(boolean locked, String key, ProceedingJoinPoint joinPoint)
      throws Throwable {
    if (!locked) {
      log.info("lock failed for {}", key);

      return null;
    }

    return joinPoint.proceed();
  }

}
