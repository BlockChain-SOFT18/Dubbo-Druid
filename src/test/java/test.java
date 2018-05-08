import jj.druid.api.AccountService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();

        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        accountService.fun();
    }
}