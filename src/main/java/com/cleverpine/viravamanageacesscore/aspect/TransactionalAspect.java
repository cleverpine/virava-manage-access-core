package com.cleverpine.viravamanageacesscore.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionalAspect {

    @Around("@annotation(com.cleverpine.viravamanageacesscore.annotation.AMTransactional)")
    public Object aroundAMTransactional(ProceedingJoinPoint joinPoint) throws Throwable {
        throw new IllegalStateException("Missing TransactionalAspect implementation in dependent library");
    }
}
