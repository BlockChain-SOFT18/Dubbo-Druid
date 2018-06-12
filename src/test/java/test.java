
import buaa.jj.accountservice.Encrypt;
import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class test {
    @Test
    public void ALogin() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        System.out.println(accountService.agencyLogin("saf","0bfe93ad6"));
        System.out.println(accountService.agencyLogin("abc","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad8"));

    }
    @Test
    public void ULogin() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        try {
            System.out.println(accountService.userLogin("saf","d7f1f1c0ad6"));
            System.out.println(accountService.userLogin("ac","0b0ad7"));
            System.out.println(accountService.userLogin("xyz","0bfe9c0d8"));
            System.out.println(accountService.userLogin("xyz","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad6"));

        } catch (Exception e) {
            if (e instanceof UserNotExistException || e instanceof UserFrozenException) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void URegistry() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        try {
            System.out.println(accountService.userRegister("xyz","fwFASDF","syc","1381412","fas@126.com","3422343",1));
            System.out.println(accountService.userRegister("af","fwFASDF","lfy","1381462","f6s@126.com","3422347",4));
            System.out.println(accountService.userRegister("af","fwFASDF","lfy","1381462","f6s@126.com","1234567890",1));
            System.out.println(accountService.userRegister("af","fwFASDF","lfy","1381462","f6s@126.com","3422347",1));
        } catch (Exception e) {
            if (e instanceof NameDuplicateException || e instanceof AgencyNotExistException || e instanceof UserAgencyDuplicateException) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void passChange() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        try {
            System.out.println(accountService.userPasswdChanging(9,"werwer","szfas"));
            System.out.println(accountService.userPasswdChanging(4,"sdfsfsdf","sdfg"));
            System.out.println(accountService.userPasswdChanging(3,"sdfsfsdf","sdfg"));
            System.out.println(accountService.userPasswdChanging(3,"0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad6","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad8"));

        } catch (Exception e) {
            if (e instanceof UserNotExistException || e instanceof UserFrozenException) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void agencyInfo() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        System.out.println(accountService.agencyInformation(3));
        System.out.println(accountService.agencyInformation(1));
    }
    @Test
    public void agencyUser() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        System.out.println(accountService.agencyAllUser(3));
        System.out.println(accountService.agencyAllUser(1));
    }
    @Test
    public void userInfo() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        System.out.println(accountService.userInformation(9));
        System.out.println(accountService.userInformation(3));
    }
    @Test
    public void Freeze() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        try {
            System.out.println(accountService.freezeUnfreeze(6,true));
            System.out.println(accountService.freezeUnfreeze(3,true));
            System.out.println(accountService.freezeUnfreeze(4,false));

        } catch (Exception e) {
            if (e instanceof UserNotExistException) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void findPasswd() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        try {
            System.out.println(accountService.foundPasswd("adfad","3253523","afafwqef32"));
            System.out.println(accountService.foundPasswd("ac","45621232","fawefw234"));
            System.out.println(accountService.foundPasswd("xyz","252454345","sftgw23"));
            System.out.println(accountService.foundPasswd("xyz","1234567890","sftgw23"));

        } catch (Exception e) {
            if (e instanceof UserNotExistException || e instanceof UserFrozenException) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void ATradeInformation() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);

        System.out.println(accountService.agencyTradeInformation(6,"2018-04-01 13:50:31","2018-05-01 13:50:31",1));
        System.out.println(accountService.agencyTradeInformation(1,"2018-04-01 13:50:31","2018-05-01 13:50:31",0));

    }
    @Test
    public void UTradeInformation() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);

        System.out.println(accountService.userTradeInformation(6,"2018-04-01 13:50:31","2018-05-01 13:50:31",1));
        System.out.println(accountService.userTradeInformation(1,"2018-04-01 13:50:31","2018-05-01 13:50:31",0));

    }
    @Test
    public void transfer() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        try {
            System.out.println(accountService.transferConsume(5,2,1.00,true));
            System.out.println(accountService.transferConsume(2,3,0.50,true));
            System.out.println(accountService.transferConsume(2,1,0.50,false));
            System.out.println(accountService.transferConsume(9,6,1.00,false));
            System.out.println(accountService.transferConsume(3,5,100.00,false));
            System.out.println(accountService.transferConsume(4,3,4.00,false));
            System.out.println(accountService.transferConsume(3,5,0.50,false));

        } catch (Exception e) {
            if (e instanceof UserNotExistException || e instanceof UserFrozenException) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void Charge() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        try {
            System.out.println(accountService.reCharge(6,4.00,false));
            System.out.println(accountService.reCharge(4,6.00,true));
            System.out.println(accountService.reCharge(3,8.00,true));

        } catch (Exception e) {
            if (e instanceof UserNotExistException) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void Draw() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Consumer.xml");
        context.start();
        AccountService accountService = (AccountService) context.getBean(AccountService.class);
        try {
            System.out.println(accountService.drawMoney(6,4.00,false));
            System.out.println(accountService.drawMoney(4,6.00,true));
            System.out.println(accountService.drawMoney(3,800.00,true));
            System.out.println(accountService.drawMoney(3,0.80,false));

        } catch (Exception e) {
            if (e instanceof UserNotExistException) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void fun() {
        System.out.println(Encrypt.SHA256("123456"));
    }
}