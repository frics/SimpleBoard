package com.example.springbootexample.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;
import java.util.List;

@Configuration
public class TransactionAspect {

    //PlatformTransactionManager를 TransactionManager로 변경
    //PlatformTransactionManager -> deprecated
    @Autowired
    private TransactionManager transactionManager;

    // Transaction 기능을 하는 advisor를 service의 모든 Impl에서 작동하도록 한다.
    private static final String EXPRESSION = "execution(* com.example.springbootexample..service.*Impl.*(..))";

    @Bean
    public TransactionInterceptor transactionAdvice() {
        //롤백이 되는 규칙 적용
        //Exception이 발생하면 어디든 rollback 시킴
        List<RollbackRuleAttribute> rollbackRules = Collections.singletonList(new RollbackRuleAttribute(Exception.class));

        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setRollbackRules(rollbackRules);
        transactionAttribute.setName("*");

        //트랜잭션 속성 적용
        MatchAlwaysTransactionAttributeSource attributeSource = new MatchAlwaysTransactionAttributeSource();
        attributeSource.setTransactionAttribute(transactionAttribute);

        return new TransactionInterceptor(transactionManager, attributeSource);
    }
    @Bean
    public Advisor transactionAdvisor(){

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(EXPRESSION);
        //트랜잭션 적용 메소드 설정 및 속성 설정
        return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
    }
}
