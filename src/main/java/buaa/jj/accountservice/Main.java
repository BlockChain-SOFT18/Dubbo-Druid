package buaa.jj.accountservice;


import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Thread {

    public final static boolean blockChain = false;
    public final static boolean clearSystem = false;

    @Override
    public void run() {
        try {
            setLogPath();
            Log4jInfoPrintStream.redirectSystemOut();
            Log4jErrorPrintStream.redirectSystemOut();
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Druid.xml","Mybatis.xml","Dubbo.xml","Beans.xml"});
            context.start();
            System.out.println("Dubbo启动！！");
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
        //Configurator.initialize(null,classpath + "log4j2.xml");
    }
}
