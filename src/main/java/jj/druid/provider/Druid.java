package jj.druid.provider;

import com.alibaba.druid.pool.DruidDataSource;
import jj.druid.api.DruidInterface;
import org.springframework.context.ApplicationContext;

public class Druid implements DruidInterface {

    DruidDataSource druidDataSource;

    public void init(ApplicationContext context) {
        druidDataSource = (DruidDataSource) context.getBean("dataSource");
    }

    public DruidDataSource getDruidDataSource() {
        return druidDataSource;
    }
}
