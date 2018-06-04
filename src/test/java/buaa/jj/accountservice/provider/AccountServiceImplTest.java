package buaa.jj.accountservice.provider;

import buaa.jj.accountservice.Encrypt;
import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.exceptions.UserFrozenException;
import buaa.jj.accountservice.exceptions.UserNotExistException;
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
        try {
            int rtn = accountService.userLogin("saf","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5424e008bb31c00c6d7f1f1c0ad6");
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void userLogin1() {
        try {
            int rtn = accountService.userLogin("ac","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad7");
            assert false;
        } catch (Exception e) {
            assert e instanceof UserFrozenException;
        }
    }

    @Test
    void userLogin2() {
        try {
            int rtn = accountService.userLogin("xyz","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad8");
            assert rtn == -1;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void userLogin3() {
        try {
            int rtn = accountService.userLogin("xyz","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad6");
            assert rtn == 3;
        } catch (Exception e) {
            assert false;
        }
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