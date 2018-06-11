package buaa.jj.accountservice.provider;

import buaa.jj.accountservice.Encrypt;
import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
        try {
            int rtn = accountService.agencyLogin("saf","0bfe93ad6");
            assert rtn == -1;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void agencyLogin1() {
        try {
            int rtn = accountService.agencyLogin("abc","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad8");
            assert rtn == -1;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void userRegister() {
        try {
            int rtn = accountService.userRegister("xyz","fwFASDF","syc","1381412","fas@126.com","3422343",1);
            assert false;
        } catch (Exception e) {
            assert e instanceof NameDuplicateException;
        }
    }

    @Test
    void userRegister1() {
        try {
            int rtn = accountService.userRegister("af","fwFASDF","lfy","1381412","fas@126.com","3422347",4);
            assert false;
        } catch (Exception e) {
            assert e instanceof AgencyNotExistException;
        }
    }

    @Test
    void userRegister2() {
        try {
            int rtn = accountService.userRegister("af","fwFASDF","lfy","1381412","fas@126.com","1234567890",1);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserAgencyDuplicateException;
        }
    }

    @Test
    void userRegister3() {
        try {
            int rtn = accountService.userRegister("af","fwFASDF","lfy","1381412","fas@126.com","3422347",1);
            assert rtn == 5;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void userPasswdChanging() {
        try {
            boolean rtn = accountService.userPasswdChanging(9,"werwer","szfas");
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void userPasswdChanging1() {
        try {
            boolean rtn = accountService.userPasswdChanging(4,"sdfsfsdf","sdfg");
            assert false;
        } catch (Exception e) {
            assert e instanceof UserFrozenException;
        }
    }

    @Test
    void userPasswdChanging2() {
        try {
            boolean rtn = accountService.userPasswdChanging(3,"sdfsfsdf","sdfg");
            assert !rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void userPasswdChanging3() {
        try {
            boolean rtn = accountService.userPasswdChanging(3,"0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad6","0bfe935e70c321c7ca3afc75ce0d0ca2f98b5422e008bb31c00c6d7f1f1c0ad8");
            assert rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void agencyInformation() {
        try {
            Map rtn = accountService.agencyInformation(3);
            assert rtn == null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void agencyInformation1() {
        try {
            Map rtn = accountService.agencyInformation(1);
            assert rtn != null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void agencyAllUser() {
        try {
            List rtn = accountService.agencyAllUser(3);
            assert rtn == null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void agencyAllUser1() {
        try {
            List rtn = accountService.agencyAllUser(1);
            assert rtn != null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void userInformation() {
        try {
            Map rtn = accountService.userInformation(9);
            assert rtn == null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void userInformation1() {
        try {
            Map rtn = accountService.userInformation(3);
            assert rtn != null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void freezeUnfreeze() {
        try {
            int rtn = accountService.freezeUnfreeze(6,true);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void freezeUnfreeze1() {
        try {
            int rtn = accountService.freezeUnfreeze(3,true);
            assert rtn == 1;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void freezeUnfreeze2() {
        try {
            int rtn = accountService.freezeUnfreeze(4,false);
            assert rtn == 2;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void foundPasswd() {
        try {
            boolean rtn = accountService.foundPasswd("adfad","3253523","afafwqef32");
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void foundPasswd1() {
        try {
            boolean rtn = accountService.foundPasswd("ac","45621232","fawefw234");
            assert false;
        } catch (Exception e) {
            assert e instanceof UserFrozenException;
        }
    }

    @Test
    void foundPasswd2() {
        try {
            boolean rtn = accountService.foundPasswd("xyz","252454345","sftgw23");
            assert !rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void foundPasswd3() {
        try {
            boolean rtn = accountService.foundPasswd("xyz","1234567890","sftgw23");
            assert rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void agencyTradeInformation() {
        try {
            List rtn = accountService.agencyTradeInformation(6,"2018-04-01 13:50:31","2018-05-01 13:50:31",1);
            assert rtn == null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void userTradeInformation() {
        try {
            List rtn = accountService.userTradeInformation(6,"2018-04-01 13:50:31","2018-05-01 13:50:31",1);
            assert rtn == null;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void transferConsume() {
        try {
            boolean rtn = accountService.transferConsume(2,3,0.50,true);
            assert rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void transferConsume1() {
        try {
            boolean rtn = accountService.transferConsume(2,1,0.50,true);
            assert rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void transferConsume2() {
        try {
            boolean rtn = accountService.transferConsume(9,6,1.00,false);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void transferConsume3() {
        try {
            boolean rtn = accountService.transferConsume(3,5,100.00,false);
            assert !rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void transferConsume4() {
        try {
            boolean rtn = accountService.transferConsume(4,3,4.00,false);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserFrozenException;
        }
    }

    @Test
    void transferConsume5() {
        try {
            boolean rtn = accountService.transferConsume(3,5,0.50,false);
            assert rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void reCharge() {
        try {
            boolean rtn = accountService.reCharge(6,4.00,false);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void reCharge1() {
        try {
            boolean rtn = accountService.reCharge(4,6.00,true);
            assert !rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void reCharge2() {
        try {
            boolean rtn = accountService.reCharge(3,8.00,true);
            assert rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void drawMoney() {
        try {
            boolean rtn = accountService.drawMoney(6,4.00,false);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserNotExistException;
        }
    }

    @Test
    void drawMoney1() {
        try {
            boolean rtn = accountService.drawMoney(4,6.00,true);
            assert false;
        } catch (Exception e) {
            assert e instanceof UserFrozenException;
        }
    }

    @Test
    void drawMoney2() {
        try {
            boolean rtn = accountService.drawMoney(3,800.00,true);
            assert !rtn;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    void drawMoney3() {
        try {
            boolean rtn = accountService.drawMoney(3,0.80,false);
            assert rtn;
        } catch (Exception e) {
            assert false;
        }
    }
}