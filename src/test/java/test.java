
import buaa.jj.accountservice.Encrypt;
import buaa.jj.accountservice.Log4jPrintStream;
import buaa.jj.accountservice.Main;
import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.exceptions.AccountServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {

    @Test
    public void Registry() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        try {
            System.out.println(accountService.userRegister("jj",Encrypt.SHA256("123456"),"jj","18610502465","1901014432@qq.com","4113030199711301015",1));
        } catch (Exception e) {
            if (e instanceof AccountServiceException) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void t() {
        Log4jPrintStream.redirectSystemOut();
        throw new RuntimeException();
    }
}