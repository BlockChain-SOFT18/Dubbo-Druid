import jj.druid.provider.Druid;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
    public static void main(String[] args) throws SQLException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        context.start();
        Druid druid = new Druid();
        druid.init(context);
        Statement statement = null;
        try {
            statement = druid.getDruidDataSource().getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM test");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(1);
        while (true) {

        }
    }
}
