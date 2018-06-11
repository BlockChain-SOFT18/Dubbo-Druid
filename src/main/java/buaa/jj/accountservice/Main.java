package buaa.jj.accountservice;


import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.log.Log4jErrorPrintStream;
import buaa.jj.accountservice.log.Log4jInfoPrintStream;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Thread {

    public static boolean blockChain = false;
    public static boolean clearSystem = false;
    public static ClassPathXmlApplicationContext context;

    @Override
    public void run() {
        try {
            setLogPath();
            Log4jInfoPrintStream.redirectSystemOut();
            Log4jErrorPrintStream.redirectSystemOut();
            context = new ClassPathXmlApplicationContext(new String[] {"Druid.xml","Mybatis.xml","Dubbo.xml","Beans.xml"});
            context.start();
            Logger logger = LogManager.getLogger("logger");
            logger.info("Dubbo启动！！");
            try {
                context.getBean(AccountService.class).CSSystemReady();
            } catch (Exception e) {
                context.getBean(AccountService.class).CSSystemClosing();
            }try {
                context.getBean(AccountService.class).BlockChainServiceReady();
            } catch (Exception e) {
                context.getBean(AccountService.class).BlockChainServiceClosing();
            }
            while (true) {
                throw new RuntimeException();
            }
        } catch (Exception e) {

        }
    }

    public static void setLogPath() {
        String classpath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        StringBuilder logPath = new StringBuilder(classpath);
        int start = logPath.indexOf("WEB-INF");
        int end = logPath.capacity();
        logPath = logPath.delete(start,end);
        logPath.append("logs");
        System.setProperty("logPath",logPath.toString());
    }
}
