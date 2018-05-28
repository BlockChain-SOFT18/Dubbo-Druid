package buaa.jj.accountservice.provider;

import buaa.jj.accountservice.Main;
import buaa.jj.accountservice.api.AccountService;
import buaa.jj.accountservice.api.BlockChainService;
import buaa.jj.accountservice.api.IUserService;
import buaa.jj.accountservice.exceptions.*;
import buaa.jj.accountservice.mybatis.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = {Exception.class, RuntimeException.class})
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;

    private SqlSessionFactory sqlSessionFactory;
    private BlockChainService blockChainService;
    private IUserService iUserService;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * 传入用户的用户名和密码然后在数据库中进行搜索，如果能找到相匹配的用户就返回用户的用户ID
     * 如果没有找到相匹配的用户就返回-1
     * @param user_name 登录的用户的用户名，类型为String
     * @param user_passwd 登录的用户的密码，类型为String
     * @return 返回用户的ID，如果没有查询到相匹配的用户就返回-1，类型为int
     */
    public int userLogin(String user_name, String user_passwd) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            return accountDao.checkUserPasswd(mapper,user_name,user_passwd);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 传入机构的用户名和密码然后在数据库中进行搜索，如果能找到相匹配的机构就返回机构的机构ID
     * 如果没有找到相匹配的机构就返回-1
     * @param agency_name 登录的机构的用户名，类型为String
     * @param agency_passwd 登录的机构的密码，类型为String
     * @return 返回机构的ID，如果没有查询到相匹配的用户就返回-1，类型为int
     */
    public int agencyLogin(String agency_name, String agency_passwd) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            return accountDao.checkAgencyPasswd(mapper,agency_name,agency_passwd);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 传入用户的用户名密码真实姓名电话号码邮箱身份证以及所属机构，将数据成功插入到数据库后注册
     * 成功，并且返回注册成功的用户ID，如果注册失败会返回相应的异常
     * @param user_name 注册的用户的用户名，类型为String
     * @param user_passwd 注册的用户的密码，类型为String
     * @param user_realname 注册的用户的真实姓名，类型为String
     * @param user_tel 注册的用户的电话号码，类型为String
     * @param user_email 注册的用户的邮箱，类型为String
     * @param user_identity 注册的用户的身份证号，类型为String
     * @param under_agency_id 注册的用户的所属机构的ID，类型为int
     * @return 返回用户的ID
     * @exception NameDuplicateException 注册用户的用户名重复，无法注册
     * @exception AgencyNotExistException 注册用户的所属机构不存在，无法注册
     * @exception UserAgencyDuplicateException 注册用户的身份证号在该机构下已注册过帐号，
     * 无法注册
     */
    public int userRegister(String user_name,
                            String user_passwd,
                            String user_realname,
                            String user_tel,
                            String user_email,
                            String user_identity,
                            int under_agency_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
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
            return accountDao.checkUserExists(mapper,"userName",user_name);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 传入用户的用户名密码真实姓名电话号码邮箱身份证以及所属机构，将数据成功插入到数据库后注册
     * 成功，并且返回注册成功的用户ID，如果注册失败会返回相应的异常
     * @param user_name 注册的用户的用户名，类型为String
     * @param user_passwd 注册的用户的密码，类型为String
     * @param user_realname 注册的用户的真实姓名，类型为String
     * @param user_tel 注册的用户的电话号码，类型为String
     * @param user_email 注册的用户的邮箱，类型为String
     * @param user_identity 注册的用户的身份证号，类型为String
     * @param under_agency_name 注册的用户的所属机构的ID，类型为int
     * @return 返回用户的ID
     * @exception NameDuplicateException 注册用户的用户名重复，无法注册
     * @exception AgencyNotExistException 注册用户的所属机构不存在，无法注册
     * @exception UserAgencyDuplicateException 注册用户的身份证号在该机构下已注册过帐号，
     * 无法注册
     */
    public int userRegister(String user_name,
                            String user_passwd,
                            String user_realname,
                            String user_tel,
                            String user_email,
                            String user_identity,
                            String under_agency_name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
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
            return accountDao.checkUserExists(mapper,"userName",user_name);
        } finally {
            sqlSession.close();
        }

    }

    /**
     * 传入更改用户的用户ID以及用户的老密码和新密码，如果输入的老密码可以和用户的密码相匹配，就
     * 将更改数据库中用户的密码，如果不匹配就返回异常
     * @param user_id 更改密码的用户的用户ID，类型为int
     * @param old_passwd 更改密码的用户的老密码，类型为String
     * @param new_passwd 更改密码的用户的新密码，类型为String
     * @return 返回true则成功更改密码
     * @exception WrongPasswordException 更改密码的用户的老密码不匹配，无法更改密码
     */
    public boolean userPasswdChanging(int user_id, String old_passwd, String new_passwd) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            if (accountDao.checkUserPasswd(mapper,user_id,old_passwd) != user_id) {
                throw new WrongPasswordException();
            }

            accountDao.updatePasswd(mapper,user_id,new_passwd);
            if (accountDao.checkUserPasswd(mapper,user_id,new_passwd) == user_id)
                throw new WrongPasswordException();
            else {
                return true;
            }
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 传入机构的ID，返回机构的所有信息，具体的Map格式看返回内容
     * @param agency_id 查询的机构的ID，类型为int
     * @return 返回机构的所有信息，agencyID，agencyName，agencyPasswd，agentName，
     * agentTel，agentEmail，所有均为String类型
     */
    public Map agencyInformation(int agency_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            return accountDao.getAgencyInformation(mapper,agency_id);
        } finally {
            sqlSession.close();
        }

    }

    /**
     * 传入机构的ID，返回机构下的所有的用户的用户ID
     * @param agency_id 查询的机构的ID，类型为int
     * @return 返回所有用户的用户ID的List，类型为List<Integer>
     */
    public List<Integer> agencyAllUser(int agency_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            return accountDao.getAgencyUsers(mapper,agency_id);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 传入用户的ID，返回用户的所有信息，具体的Map格式看返回内容
     * @param user_id 查询的用户的ID，类型为int
     * @return 返回用户的所有信息，userID,userName,userPasswd,userTel,userEmail,
     * userIdentity,agency,availableBalance,frozenBalance,isFrozen，所有均为
     * String类型
     */
    public Map userInformation(int user_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            return accountDao.getUserInformation(mapper,user_id);
        } finally {
            sqlSession.close();
        }

    }

    /**
     * 传入冻结用户的用户ID和是否冻结
     * @param user_id 冻结用户的用户ID，类型为int
     * @param is_frozen 是否冻结，类型为boolean
     * @return 如果成功变更冻结状态则返回1
     * @exception UserNotExsistException 不存在该ID的用户
     */
    public int freezeUnfreeze(int user_id, boolean is_frozen) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            if (accountDao.checkUserExists(mapper,"userID",""+user_id) == -1)
                throw new UserNotExsistException();
            accountDao.updateFrozen(mapper,user_id, is_frozen);
            return 1;
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 传入找回密码的用户的用户名身份证号以及新的密码，如果成功找回密码则返回true
     * @param user_name 找回密码的用户的用户名，类型为String
     * @param user_identity 找回密码的用户的身份证号，类型为String
     * @param new_passwd 找回密码的用户的新密码，类型为String
     * @return 如果成功查询到返回false
     */
    public boolean foundPasswd(String user_name, String user_identity, String new_passwd) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            int id = accountDao.checkUserExists(mapper,"userName",user_name);
            if (id != -1) {
                accountDao.updatePasswd(mapper,id,new_passwd);
            } else {
                throw new UserNotExsistException();
            }
            return true;
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 获取区块链中所有机构相关的交易信息，
     * @param agency_id
     * @param start_date
     * @param end_date
     * @param trade_type
     * @return
     */
    public List<Map<Integer, String>> agencyTradeInformation(int agency_id, String start_date, String end_date, int trade_type) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            List<Integer> idList1 = accountDao.getTransactionID(mapper,null,agency_id,start_date,end_date,true);
            List<Integer> idList2 = accountDao.getTransactionID(mapper,null,agency_id,start_date,end_date,false);
            if (Main.blockChain) {
                List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
                for (Integer i : idList1) {
                    Map<Integer, String> map = new HashMap<Integer, String>();
                    String str = blockChainService.QueryTransaction(i);
                    map.put(i,str);
                    list.add(map);
                }
                for (Integer i : idList2) {
                    Map<Integer, String> map = new HashMap<Integer, String>();
                    String str = blockChainService.QueryBalanceChange(i);
                    map.put(i,str);
                    list.add(map);
                }
                return list;
            }
            return null;
        } finally {
            sqlSession.close();
        }
    }

    public List<Map<Integer, String>> userTradeInformation(int user_id, String start_date, String end_date, int trade_type) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            String s = "make ide happy";
            List<Integer> idList1 = accountDao.getTransactionID(mapper,user_id,null,start_date,end_date,true);
            List<Integer> idList2 = accountDao.getTransactionID(mapper,user_id,null,start_date,end_date,false);
            if (Main.blockChain) {

                List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
                for (Integer i : idList1) {
                    Map<Integer, String> map = new HashMap<Integer, String>();
                    String str = blockChainService.QueryTransaction(i);
                    map.put(i,str);
                    list.add(map);
                    String s1 = "make ide happier";
                }
                for (Integer i : idList2) {
                    Map<Integer, String> map = new HashMap<Integer, String>();
                    String str = blockChainService.QueryBalanceChange(i);
                    map.put(i,str);
                    list.add(map);
                    String s1 = "make ide happiest";
                }
                return list;
            }
            return null;
        } finally {
            sqlSession.close();
        }
    }

    public boolean transferConsume(int pay_user_id, int get_user_id, double amount, boolean trade_type) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            if (pay_user_id <= 2 || get_user_id <= 2) {
                //清洁算平台调用部分
                if (pay_user_id > 2 && get_user_id == 2) {
                    //消费时清洁算平台调用扣除费用
                    accountDao.minusBalance(mapper,pay_user_id,amount);
                    accountDao.addLiquidationBalance(mapper,amount);
                } else if (pay_user_id == 2 && get_user_id == 1) {
                    //清洁算时清洁算平台调用转帐到平台收入
                    accountDao.minusLiquidationBalance(mapper,amount);
                    accountDao.addPlatformBalance(mapper,amount);
                } else if (pay_user_id == 2 && get_user_id > 2 ) {
                    //清分时清洁算平台调用支付商户金钱
                    accountDao.minusLiquidationBalance(mapper,amount);
                    accountDao.addBalance(mapper,get_user_id,amount);
                }
            } else {
                //普通用户消费或转帐
                //获取账户信息
                Map map1 = accountDao.getUserInformation(mapper,pay_user_id);
                Map map2 = accountDao.getUserInformation(mapper,get_user_id);
                //判断用户是否存在以及是否有足够的可用余额
                if (map1 == null || map2 == null) {
                    throw new UserNotExsistException();
                } else if ((Integer) map1.get("availableBalance") < amount) {
                    return false;
                }
                //生成消费ID
                int agencyid1 = (Integer) map1.get("agency");
                int agencyid2 = (Integer) map2.get("agency");
                StringBuilder s = new StringBuilder();
                s.append(0);
                s.append(agencyid1).append(0);
                s.append(pay_user_id);
                s.append(agencyid2).append(0);
                s.append(get_user_id);
                String datetime = generatorID(s);
                if (trade_type) {
                    //用户消费调用清洁算平台
                    s.append(1);
                    if (Main.clearSystem) {
                        iUserService.Consume(pay_user_id,get_user_id,datetime,s.toString(),(float) amount,true);
                    }
                }
                else {
                    //用户转帐扣费并且实时到帐
                    accountDao.minusBalance(mapper,pay_user_id,amount);
                    accountDao.addBalance(mapper,get_user_id,amount);
                    s.append(0);
                }
                //将消费信息计入消费数据库并且调用区块链接口入链
                accountDao.insertTransaction(mapper,s.toString(),0,datetime,agencyid1,pay_user_id,agencyid2,get_user_id,amount);
                if (Main.blockChain) {
                    blockChainService.InsertTransaction(Integer.parseInt(s.toString()),agencyid1,pay_user_id,agencyid2,get_user_id,datetime,trade_type,amount);
                }
            }
            return true;
        } finally {
            sqlSession.close();
        }
    }

    public boolean reCharge(int user_id, double amount, boolean recharge_platform) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            //获取用户信息
            Map map = accountDao.getUserInformation(mapper,user_id);
            //判断用户是否存在
            if (map == null) {
                throw new UserNotExsistException();
            }
            //生成交易ID
            int agencyid = (Integer) map.get("agency");
            StringBuilder s = new StringBuilder();
            s.append(1);
            s.append(agencyid).append(0);
            s.append(user_id);
            //增加用户余额并且在加入交易数据库后入链
            accountDao.addBalance(mapper,user_id,amount);
            String datetime = generatorID(s);
            accountDao.insertTransaction(mapper,s.toString(),1,datetime,null,null,agencyid,user_id,amount);
            if (Main.blockChain) {
                blockChainService.InsertBalanceChange(Integer.parseInt(s.toString()),agencyid,user_id,datetime,recharge_platform,amount);
            }
            //调用清洁算平台提供的充值接口
            if (recharge_platform) {
                s.append(1);
                if (Main.clearSystem) {
                    iUserService.Recharge(user_id,s.toString(),datetime,(float) amount,recharge_platform,true);
                }
            }
            else {
                s.append(0);
                if (Main.clearSystem) {
                    iUserService.Recharge(user_id,s.toString(),datetime,(float) amount,recharge_platform,true);
                }
            }
            return true;
        } finally {
            sqlSession.close();
        }
    }

    public boolean drawMoney(int user_id, double amount, boolean draw_platform) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            //获取用户信息
            Map map = accountDao.getUserInformation(mapper,user_id);
            //判断用户是否存在
            if (map == null) {
                throw new UserNotExsistException();
            } else if ((Integer) map.get("availableBalance") < amount) {
                return false;
            }
            //生成交易ID
            int agencyid = (Integer) map.get("agency");
            StringBuilder s = new StringBuilder();
            s.append(2);
            s.append(agencyid).append(0);
            s.append(user_id);
            //扣除用户余额并且在加入交易数据库后入链
            accountDao.minusBalance(mapper,user_id,amount);
            String datetime = generatorID(s);
            accountDao.insertTransaction(mapper,s.toString(),2,datetime,agencyid,user_id,null,null,amount);
            if (Main.blockChain) {
                blockChainService.InsertBalanceChange(Integer.parseInt(s.toString()),agencyid,user_id,datetime,draw_platform,amount);
            }
            if (draw_platform) {
                s.append(1);
                if (Main.clearSystem) {
                    iUserService.Withdraw(user_id,datetime,s.toString(),(float) amount,draw_platform,true);
                }
            }
            else {
                s.append(0);
                if (Main.clearSystem) {
                    iUserService.Withdraw(user_id,datetime,s.toString(),(float) amount,draw_platform,true);
                }
            }
            return true;
        } finally {
            sqlSession.close();
        }
    }

    private String generatorID(StringBuilder s) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = dateFormat.format(new Date());
        StringBuilder stringBuilder = new StringBuilder(datetime);
        int a[] = {4,6,8,10,12};
        for (int n : a) {
            stringBuilder.deleteCharAt(n);
        }
        s.insert(0,stringBuilder.toString());
        return datetime;
    }
}
