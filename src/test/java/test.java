import jj.druid.provider.Druid;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
    public static void main(String[] args) throws SQLException {
        Druid druid = new Druid();
        druid.getDruidDataSource();
    }
}
