package jj.druid.provider;

import com.alibaba.druid.pool.DruidDataSource;
import jj.druid.api.DruidInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Druid implements DruidInterface {

    DruidDataSource druidDataSource;
    Statement statement;

    public Druid() {
        init();
    }

    public void init() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Druid.xml");
        druidDataSource = (DruidDataSource) context.getBean("dataSource");
    }

    public boolean execute(String sql) throws SQLException {
        statement = druidDataSource.getConnection().createStatement();
        boolean state = statement.execute(sql);
        statement.close();
        return state;
    }


    public List<Map<String, String>> executeQuery(String sql) throws SQLException {
        statement = druidDataSource.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                map.put(rsmd.getColumnName(i),rs.getString(i));
            }
            list.add(map);
            map = new HashMap<String, String>();
        }
        rs.close();
        statement.close();
        return list;
    }

    public int executeUpdate(String sql) throws SQLException {
        statement = druidDataSource.getConnection().createStatement();
        int n = statement.executeUpdate(sql);
        statement.close();
        return n;
    }
    public DruidDataSource getDruidDataSource() {
        return druidDataSource;
    }
}
