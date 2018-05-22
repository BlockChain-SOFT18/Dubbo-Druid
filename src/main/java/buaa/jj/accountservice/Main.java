package buaa.jj.accountservice;

import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.exceptions.AccountServiceException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Thread {
    @Override
    public void run() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Druid.xml","Mybatis.xml","Dubbo.xml","Beans.xml"});
        context.start();
        while (true) {

        }

    }
}
