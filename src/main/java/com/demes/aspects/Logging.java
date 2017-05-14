package com.demes.aspects;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class Logging {
    @Pointcut("within(com.demes..*)")
    public void logging() {
    }

    @Around("logging()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        final Signature signature = joinPoint.getSignature();
        long start = System.nanoTime();
        log.debug("Starting to execute " + signature.getDeclaringTypeName()
                + "." + signature.getName());
        Object retVal = joinPoint.proceed();
        log.debug("Finished to execute " + signature.getDeclaringTypeName() + "." + signature.getName() +
                "Method speed : " + (System.nanoTime() - start) + " ns");
        return retVal;
    }
}
