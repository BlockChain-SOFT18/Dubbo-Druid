import org.springframework.context.support.ClassPathXmlApplicationContext;

public class dubbo_test {
    public static void main(String [] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Dubbo.xml");
        context.start();
        while (true) {

        }
    }
}
