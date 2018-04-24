import org.springframework.context.support.ClassPathXmlApplicationContext;

public class dubbo_test {
    public static void main(String [] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        context.start();
        while (true) {

        }
    }
}
