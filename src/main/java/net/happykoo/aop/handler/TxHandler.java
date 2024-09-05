package net.happykoo.aop.handler;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TxHandler implements InvocationHandler {

    private PlatformTransactionManager transactionManager;
    private Object target;
    private String pattern;

    public TxHandler(PlatformTransactionManager transactionManager, Object target, String pattern) {
        this.transactionManager = transactionManager;
        this.target = target;
        this.pattern = pattern;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith(pattern)) {
            TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
            try {
                Object result = method.invoke(target, args);
                transactionManager.commit(status);
                return result;

            } catch (Exception e) {
                transactionManager.rollback(status);
                throw new RuntimeException(e);
            }
        } else {
            return method.invoke(target, args);
        }
    }
}
