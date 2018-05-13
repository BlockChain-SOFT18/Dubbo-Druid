package buaa.jj.accountservice.provider;

import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.exceptions.*;
import buaa.jj.accountservice.mybatis.Mapper;
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
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        System.out.println(accountDao.checkAgencyExists(mapper,"agencyID","1"));
        sqlSession.close();
        throw new AccountServiceException();
    }

    public int userLogin(String user_name, String user_passwd) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        int id = accountDao.checkUserPasswd(mapper,user_name,user_passwd);
        sqlSession.close();
        return id;
    }

    public int agencyLogin(String agency_name, String agency_passwd) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        int id = accountDao.checkAgencyPasswd(mapper,agency_name,agency_passwd);
        sqlSession.close();
        return id;
    }

    public int userRegister(String user_name,
                            String user_passwd,
                            String user_realname,
                            String user_tel,
                            String user_email,
                            String user_identity,
                            int under_agency_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        if (accountDao.checkUserExists(mapper,"userName",user_name) != -1) {
            throw new NameDuplicateException();
        }
        if (accountDao.checkAgencyExists(mapper,"agencyID",""+under_agency_id) == -1) {
            throw new AgencyNotExistException();
        }
        if (accountDao.checkUserAgencyDuplicate(mapper,user_identity,under_agency_id) != -1) {
            throw new UserAgencyDuplicateException();
        }
        accountDao.userInsert(mapper,user_name,user_passwd,user_realname,user_tel,user_email,user_identity,under_agency_id);
        int id = accountDao.checkUserExists(mapper,"userName",user_name);
        sqlSession.close();
        return id;
    }

    public int userRegister(String user_name,
                            String user_passwd,
                            String user_realname,
                            String user_tel,
                            String user_email,
                            String user_identity,
                            String under_agency_name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        int agency_id = accountDao.checkAgencyExists(mapper,"agencyName",under_agency_name);
        if (agency_id == -1) {
            throw new AgencyNotExistException();
        }
        if (accountDao.checkUserExists(mapper,"userName",user_name) != -1) {
            throw new NameDuplicateException();
        }
        if (accountDao.checkUserAgencyDuplicate(mapper,user_identity,agency_id) != -1) {
            throw new UserAgencyDuplicateException();
        }
        accountDao.userInsert(mapper,user_name,user_passwd,user_realname,user_tel,user_email,user_identity,agency_id);
        int id = accountDao.checkUserExists(mapper,"userName",user_name);
        sqlSession.close();
        return id;
    }

    public boolean userPasswdChanging(int user_id, String old_passwd, String new_passwd) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        if (accountDao.checkUserPasswd(mapper,user_id,old_passwd) != user_id) {
            throw new WrongPasswordException();
        }

        accountDao.updatePasswd(mapper,user_id,new_passwd);
        if (accountDao.checkUserPasswd(mapper,user_id,new_passwd) == user_id)
            throw new WrongPasswordException();
        else {
            sqlSession.close();
            return true;
        }
    }

    public Map agencyInformation(int agency_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        Map map = accountDao.getAgencyInformation(mapper,agency_id);
        sqlSession.close();
        return map;
    }

    public List<Integer> agencyAllUser(int agency_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        List<Integer> list = accountDao.getAgencyUsers(mapper,agency_id);
        sqlSession.close();
        return list;
    }

    public Map userInformation(int user_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        Map map = accountDao.getUserInformation(mapper,user_id);
        sqlSession.close();
        return map;
    }

    public int freezeUnfreeze(int user_id, boolean is_frozen) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        if (accountDao.checkUserExists(mapper,"userID",""+user_id) == -1)
            throw new UserNotExsistException();
        accountDao.updateFrozen(mapper,user_id, is_frozen);
        sqlSession.close();
        return 1;
    }

    public boolean foundPasswd(String user_name, String user_identity, String new_passwd) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        int id = accountDao.checkUserExists(mapper,"userName",user_name);
        if (id != -1) {
            accountDao.updatePasswd(mapper,id,new_passwd);
        } else {
            throw new UserNotExsistException();
        }
        sqlSession.close();
        return false;
    }

    public List<Map<Integer, String>> agencyTradeInformation(int agency_id, String start_date, String end_date, int trade_type) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        sqlSession.close();
        return null;
    }

    public List<Map<Integer, String>> userTradeInformation(int user_id, String start_date, String end_date, int trade_type) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        sqlSession.close();
        return null;
    }

    public boolean transferConsume(int pay_user_id, int get_user_id, double amount, boolean trade_type) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        sqlSession.close();
        return false;
    }

    public boolean reCharge(int user_id, double amount, boolean recharge_platform) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        sqlSession.close();
        return false;
    }

    public boolean drawMoney(int user_id, double amount, boolean draw_platform) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        sqlSession.close();
        return false;
    }
}
