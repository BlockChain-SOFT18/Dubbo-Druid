package buaa.jj.accountservice.provider;

import buaa.jj.accountservice.Encrypt;
import buaa.jj.accountservice.api.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    }

    @Test
    void agencyLogin() {
    }

    @Test
    void userRegister() {
    }

    @Test
    void userRegister1() {
    }

    @Test
    void userPasswdChanging() {
    }

    @Test
    void agencyInformation() {
    }

    @Test
    void agencyAllUser() {
    }

    @Test
    void userInformation() {
    }

    @Test
    void freezeUnfreeze() {
    }

    @Test
    void foundPasswd() {
    }

    @Test
    void agencyTradeInformation() {
    }

    @Test
    void userTradeInformation() {
    }

    @Test
    void transferConsume() {
        accountService.transferConsume(5,5,1,true);
    }

    @Test
    void reCharge() {
        accountService.reCharge(5,1,true);
    }

    @Test
    void drawMoney() {
        accountService.drawMoney(5,1,true);
    }
}