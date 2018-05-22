package buaa.jj.accountservice.provider;

import buaa.jj.accountservice.Encrypt;
import buaa.jj.accountservice.api.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class AccountServiceImplTest {

    AccountService accountService;

    AccountServiceImplTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Beans.xml","Druid.xml","Mybatis.xml"});
        context.start();
        accountService = (AccountService) context.getBean("accountService");
    }

    @Test
    void userLogin() {
        System.out.println(Encrypt.SHA256("123456").length());
        System.out.println(accountService.userLogin("jj","123456"));
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
    }

    @Test
    void reCharge() {
    }

    @Test
    void drawMoney() {
    }
}