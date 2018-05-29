
import buaa.jj.accountservice.Encrypt;
import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.exceptions.AccountServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {

    @Test
    public void Registry() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        try {

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