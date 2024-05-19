package org.example.capstonebackend.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    //responsible for logging method executions.

    // Define a pointcut that matches methods within a specific package and its subpackages
    @Pointcut("execution(* org.example.capstonebackend.service..*.*(..))")
    public void controllerMethods() {}

    // Define an advice method to run around the matched methods
    @Around("controllerMethods()")
    public Object logAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // Log a message indicating the method is about to be executed
        log.info("Before method execution: " + joinPoint.getSignature().toShortString());

        try {
            // Proceed with the method invocation
            Object result = joinPoint.proceed();
            // Log a message indicating the method has been executed
            log.info("After method execution: " + joinPoint.getSignature().toShortString());
            // Return the result of the method invocation
            return result;
        } catch (Throwable throwable) {
            // Log an error message if an exception occurs during method execution
            log.error("Exception in method: " + joinPoint.getSignature().toShortString(), throwable);
            // Rethrow the exception to propagate it further
            throw throwable; // Rethrow the exception
        }
    }
}
