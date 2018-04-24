package jj.druid;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Thread {
    @Override
    public void run() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Druid.xml","Mybatis.xml","Dubbo.xml"});
        context.start();
        while (true) {

        }
    }
}
