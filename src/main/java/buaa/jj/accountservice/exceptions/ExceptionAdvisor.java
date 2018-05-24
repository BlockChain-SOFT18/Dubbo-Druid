package buaa.jj.accountservice.exceptions;

import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.aop.ThrowsAdvice;

public class ExceptionAdvisor implements ThrowsAdvice {

    public void afterThrowing(Exception ex) throws Throwable {
        System.out.println("2131231231");
        System.out.println("12312321"+ex);
        throw ex;
    }
}
