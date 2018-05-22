package buaa.jj.accountservice.provider;

import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.mybatis.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class AccountDaoTest {

    Mapper mapper;
    AccountDao accountDao;

    AccountDaoTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Beans.xml","Druid.xml","Mybatis.xml"});
        context.start();
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) context.getBean("sqlSessionFactory");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        mapper = sqlSession.getMapper(Mapper.class);
        accountDao = (AccountDao) context.getBean("accountDao");
    }

    @org.junit.jupiter.api.Test
    void checkAgencyExists() {
    }

    @org.junit.jupiter.api.Test
    void checkUserExists() {
    }

    @org.junit.jupiter.api.Test
    void checkUserAgencyDuplicate() {
    }

    @org.junit.jupiter.api.Test
    void checkUserPasswd() {
    }

    @org.junit.jupiter.api.Test
    void checkUserPasswd1() {
    }

    @org.junit.jupiter.api.Test
    void checkAgencyPasswd() {
    }

    @org.junit.jupiter.api.Test
    void checkAgencyPasswd1() {
    }

    @org.junit.jupiter.api.Test
    void getUserBalance() {
    }

    @org.junit.jupiter.api.Test
    void userInsert() {
    }

    @org.junit.jupiter.api.Test
    void updatePasswd() {
    }

    @org.junit.jupiter.api.Test
    void getUserInformation() {
    }

    @org.junit.jupiter.api.Test
    void getAgencyInformation() {
    }

    @org.junit.jupiter.api.Test
    void getAgencyUsers() {
    }

    @org.junit.jupiter.api.Test
    void checkUserIdentity() {
    }

    @org.junit.jupiter.api.Test
    void updateFrozen() {
    }

    @org.junit.jupiter.api.Test
    void addBalance() {
    }

    @org.junit.jupiter.api.Test
    void minusBalance() {
    }

    @org.junit.jupiter.api.Test
    void addPlatformBalance() {
    }

    @org.junit.jupiter.api.Test
    void minusPlatformBalance() {
    }

    @org.junit.jupiter.api.Test
    void addLiquidationBalance() {
    }

    @org.junit.jupiter.api.Test
    void minusLiquidationBalance() {
    }
}