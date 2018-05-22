
import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.exceptions.AccountServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {

    @Test
    public void fun() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();

        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        try {
            accountService.fun();
        } catch (Exception e) {
            if (e instanceof AccountServiceException) {
                e.printStackTrace();
            }
        }
    }
}