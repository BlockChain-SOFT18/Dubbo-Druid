import jj.druid.provider.Druid;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Druid.xml","Mybatis.xml"});
        context.start();
        final SqlSessionFactory factory = (SqlSessionFactory) context.getBean("sqlSessionFactory");
        SqlSession session = factory.openSession();
        List l = session.selectList("selectAll");
        System.out.println(l);
        Thread thread = new Thread() {
            public void run() {

            }
        };
    }
}
