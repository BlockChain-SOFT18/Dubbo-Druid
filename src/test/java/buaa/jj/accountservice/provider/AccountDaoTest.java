package buaa.jj.accountservice.provider;

import buaa.jj.accountservice.Encrypt;
import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.mybatis.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AccountDaoTest {

    Mapper mapper;
    AccountDao accountDao;

    AccountDaoTest() {
        System.setProperty("logPath","./logs");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Beans.xml","Druid.xml","Mybatis.xml"});
        context.start();
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) context.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        mapper = sqlSession.getMapper(Mapper.class);
        accountDao = (AccountDao) context.getBean("accountDao");
    }

    @org.junit.jupiter.api.Test
    void checkAgencyExists() {
        System.out.println(accountDao.checkAgencyExists(mapper,"agencyID",1 + ""));
        System.out.println(accountDao.checkAgencyExists(mapper,"agencyName","agency2"));
    }

    @org.junit.jupiter.api.Test
    void checkUserExists() {
        HashMap map = new HashMap();
        map.put("userName","jj4");
        System.out.println(mapper.selectUserID(map));
        System.out.println(accountDao.checkUserExists(mapper,"userName","jj"));
        System.out.println(accountDao.checkUserExists(mapper,"userID",6 + ""));

    }

    @org.junit.jupiter.api.Test
    void checkUserAgencyDuplicate() {
        System.out.println(accountDao.checkUserAgencyDuplicate(mapper,"411303199711301015",1));
        System.out.println(accountDao.checkUserAgencyDuplicate(mapper,"411303199711301014",1));
    }

    @org.junit.jupiter.api.Test
    void checkUserPasswd() {
        System.out.println(accountDao.checkUserPasswd(mapper,"jj",Encrypt.SHA256("123456")));
        System.out.println(accountDao.checkUserPasswd(mapper,4,Encrypt.SHA256("123456")));
        System.out.println(accountDao.checkUserPasswd(mapper,5,Encrypt.SHA256("123123")));
    }

    @org.junit.jupiter.api.Test
    void checkUserPasswd1() {
    }

    @org.junit.jupiter.api.Test
    void checkAgencyPasswd() {
        System.out.println(accountDao.checkAgencyPasswd(mapper,"agency1",Encrypt.SHA256("123456")));
        System.out.println(accountDao.checkAgencyPasswd(mapper,2,Encrypt.SHA256("123456")));
    }

    @org.junit.jupiter.api.Test
    void checkAgencyPasswd1() {
    }

    @org.junit.jupiter.api.Test
    void getUserBalance() {
        System.out.println(accountDao.getUserBalance(mapper,3));
    }

    @org.junit.jupiter.api.Test
    void userInsert() {
        accountDao.userInsert(mapper,"jj", Encrypt.SHA256("123456"),"jj","18610502465","1901014432@qq.com","411303199711301015",1);
        accountDao.userInsert(mapper,"jj1",Encrypt.SHA256("123456"),"jj","18610502465","1901014432@qq.com","411303199711301014",1);
        accountDao.userInsert(mapper,"jj2",Encrypt.SHA256("123456"),"jj","18610502465","1901014432@qq.com","411303199711301015",2);
    }

    @org.junit.jupiter.api.Test
    void updatePasswd() {
        accountDao.updatePasswd(mapper,3,Encrypt.SHA256("123123"));
    }

    @org.junit.jupiter.api.Test
    void getUserInformation() {
        System.out.println(accountDao.getUserInformation(mapper,3));
    }

    @org.junit.jupiter.api.Test
    void getAgencyInformation() {
        System.out.println(accountDao.getAgencyInformation(mapper,1));
    }

    @org.junit.jupiter.api.Test
    void getAgencyUsers() {
        System.out.println(accountDao.getAgencyUsers(mapper,1));
    }

    @org.junit.jupiter.api.Test
    void checkUserIdentity() {
        System.out.println(accountDao.checkUserIdentity(mapper,"jj","411303199711301015"));
    }

    @org.junit.jupiter.api.Test
    void updateFrozen() {
        accountDao.updateFrozen(mapper,3,true);
    }

    @org.junit.jupiter.api.Test
    void addBalance() {
        accountDao.addBalance(mapper,3,100);
    }

    @org.junit.jupiter.api.Test
    void minusBalance() {
        accountDao.minusBalance(mapper,3,100);
    }

    @org.junit.jupiter.api.Test
    void addPlatformBalance() {
        accountDao.addPlatformBalance(mapper,10);
    }

    @org.junit.jupiter.api.Test
    void minusPlatformBalance() {
        accountDao.minusPlatformBalance(mapper,10);
    }

    @org.junit.jupiter.api.Test
    void addLiquidationBalance() {
        accountDao.addLiquidationBalance(mapper,10.1);
    }

    @org.junit.jupiter.api.Test
    void minusLiquidationBalance() {
        accountDao.minusLiquidationBalance(mapper,10.2);
    }

    @Test
    void getUserIfFrozen() {
        System.out.println(accountDao.getUserIfFrozen(mapper,1));
    }
}