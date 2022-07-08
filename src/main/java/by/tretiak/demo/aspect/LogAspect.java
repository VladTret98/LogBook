package by.tretiak.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository.save(..))")
    public void savingUsersMethodsPointcut() { }

    @After("savingUsersMethodsPointcut()")
    public void afterSavingMethods(JoinPoint joinPoint) {
        logger.info("New data was successfully saved: " + joinPoint.toString());
    }

    @Pointcut("execution( * by.tretiak.demo.service.*.add* (..))")
    public void exceptionsPointcut(){ }


    @AfterThrowing(pointcut = "exceptionsPointcut()")
    public void exceptionsAfterThrowing(JoinPoint joinPoint){
        logger.warn("Exception throws in " + joinPoint.getSignature().getName());
    }


}
