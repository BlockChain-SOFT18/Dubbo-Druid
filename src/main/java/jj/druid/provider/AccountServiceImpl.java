package jj.druid.provider;

import jj.druid.api.AccountService;
import jj.druid.mybatis.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = {Exception.class, RuntimeException.class})
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;

    private SqlSessionFactory sqlSessionFactory;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void fun(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(jj.druid.mybatis.Mapper.class);
        System.out.println(accountDao.checkAgencyExists(mapper,"agencyID","1"));
        sqlSession.close();
    }

    public int userLogin(String user_name, String user_passwd) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(jj.druid.mybatis.Mapper.class);
        sqlSession.close();
        return 0;
    }

    public int agencyLogin(String agency_name, String agency_passwd) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(jj.druid.mybatis.Mapper.class);
        sqlSession.close();
        return 0;
    }

    public int userRegister(String user_name,
                            String user_passwd,
                            String user_realname,
                            String user_tel,
                            String user_email,
                            String user_identity,
                            int under_agency_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(jj.druid.mybatis.Mapper.class);
        sqlSession.close();
        return 0;
    }

    public int userRegister(String user_name,
                            String user_passwd,
                            String user_realname,
                            String user_tel,
                            String user_email,
                            String user_identity,
                            String under_agency_name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(jj.druid.mybatis.Mapper.class);
        sqlSession.close();
        return 0;
    }

    public boolean userPasswdChanging(int user_id, String old_passwd, String new_passwd) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(jj.druid.mybatis.Mapper.class);
        sqlSession.close();
        return false;
    }

    public List<Map<String, String>> agencyInformation(int agency_id) {
        return null;
    }

    public List<Map<Integer, Integer>> agencyAllUser(int agency_id) {
        return null;
    }

    public List<Map<String, String>> userInformation(int user_id) {
        return null;
    }

    public int freezeUnfreeze(int user_id, boolean if_freeze) {
        return 0;
    }

    public boolean foundPasswd(String user_name, String user_identity, String new_passwd) {
        return false;
    }

    public List<Map<Integer, String>> agencyTradeInformation(int agency_id, String start_date, String end_date, int trade_type) {
        return null;
    }

    public List<Map<Integer, String>> userTradeInformation(int user_id, String start_date, String end_date, int trade_type) {
        return null;
    }

    public boolean transferConsume(int pay_user_id, int get_user_id, double amount, boolean trade_type) {
        return false;
    }

    public boolean reCharge(int user_id, double amount, boolean recharge_platform) {
        return false;
    }

    public boolean drawMoney(int user_id, double amount, boolean draw_platform) {
        return false;
    }
}
