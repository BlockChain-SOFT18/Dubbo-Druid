package jj.druid;

import jj.druid.provider.Druid;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Thread {
    @Override
    public void run() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        context.start();
        while (true) {

        }
    }
}
