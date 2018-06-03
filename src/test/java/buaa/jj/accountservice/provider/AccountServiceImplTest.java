package buaa.jj.accountservice.provider;

import buaa.jj.accountservice.Encrypt;
import buaa.jj.accountservice.api.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

class AccountServiceImplTest {

    AccountService accountService;

    AccountServiceImplTest() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Beans.xml","Druid.xml","Mybatis.xml"});
        context.start();
        accountService = (AccountService) context.getBean("accountService");
    }

    @Test
    void userLogin() {
        System.out.println(accountService.userLogin("jj1",Encrypt.SHA256("123456")));
        System.out.println(accountService.userLogin("jj",Encrypt.SHA256("123456")));
    }

    @Test
    void agencyLogin() {
        System.out.println(accountService.agencyLogin("agency1",Encrypt.SHA256("123456")));
        System.out.println(accountService.agencyLogin("agency3",Encrypt.SHA256("123456")));
    }

    @Test
    void userRegister() {
        System.out.println(accountService.userRegister("jj9",Encrypt.SHA256("123456"),"jj","123456","1901014432@qq.com","411303199711301011","agency2"));
    }

    @Test
    void userRegister1() {
    }

    @Test
    void userPasswdChanging() {
        System.out.println(accountService.userPasswdChanging(4,Encrypt.SHA256("123456"),Encrypt.SHA256("1234567")));
    }

    @Test
    void agencyInformation() {
        System.out.println(accountService.agencyInformation(1));
    }

    @Test
    void agencyAllUser() {
        System.out.println(accountService.agencyAllUser(2));
    }

    @Test
    void userInformation() {
        System.out.println(accountService.userInformation(3));
    }

    @Test
    void freezeUnfreeze() {
        System.out.println(accountService.freezeUnfreeze(3,false));
    }

    @Test
    void foundPasswd() {
        System.out.println(accountService.foundPasswd("jj","411303199711301015",Encrypt.SHA256("123456")));
    }

    @Test
    void agencyTradeInformation() {
    }

    @Test
    void userTradeInformation() {
    }

    @Test
    void transferConsume() {
        accountService.transferConsume(3,4,200,true);
    }

    @Test
    void reCharge() {
        accountService.reCharge(5,1,false);
    }

    @Test
    void drawMoney() {
        accountService.drawMoney(5,1,true);
    }

    @Test
    void cachetest() {
        System.out.println(accountService.freezeUnfreeze(3,false));
        System.out.println(accountService.userInformation(3));
        System.out.println(accountService.reCharge(3,100,true));
        System.out.println(accountService.userInformation(3));
        System.out.println(accountService.freezeUnfreeze(3,true));
        System.out.println(accountService.userInformation(3));

    }

    @Test
    void a() {
        //363484.12
        double a = 36348111111114.12;
        float b = (float) a;
        BigDecimal c = new BigDecimal("36348111111114.12");

        System.out.println(a + " " + b + " " + c.compareTo(new BigDecimal("36348111111114.11")));
    }
}