
import buaa.jj.accountservice.Encrypt;
import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.exceptions.AccountServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class test {

    @Test
    public void Registry() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        try {
            System.out.println(accountService.userRegister("jj91",Encrypt.SHA256("123456"),"jj","123456","1901014432@qq.com","411303199711301010","agency2"));
        } catch (Exception e) {
            if (e instanceof AccountServiceException) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void funs() {
        java.sql.Date date = new java.sql.Date(1);
        System.out.println(date);
    }
}