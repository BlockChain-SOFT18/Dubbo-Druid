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

import java.math.BigDecimal;
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
     * @exception UserNotExistException 用户不存在，无法登录
     * @exception UserFrozenException 用户被冻结，无法登录
     */
    public int userLogin(String user_name, String user_passwd) {
        System.out.println("用户：" + user_name + "，密码：" + user_passwd + "，请求登录");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        int id = accountDao.checkUserExists(mapper,"userName",user_name);
        if (id == -1)
            throw new UserNotExistException();
        if (accountDao.getUserIfFrozen(mapper,id))
            throw new UserFrozenException();
        return accountDao.checkUserPasswd(mapper,user_name,user_passwd);
    }

    /**
     * 传入机构的用户名和密码然后在数据库中进行搜索，如果能找到相匹配的机构就返回机构的机构ID
     * 如果没有找到相匹配的机构就返回-1
     * @param agency_name 登录的机构的用户名，类型为String
     * @param agency_passwd 登录的机构的密码，类型为String
     * @return 返回机构的ID，如果没有查询到相匹配的用户就返回-1，类型为int
     */
    public int agencyLogin(String agency_name, String agency_passwd) {
        System.out.println("机构：" + agency_name + "，密码：" + agency_passwd + "，请求登录");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        return accountDao.checkAgencyPasswd(mapper,agency_name,agency_passwd);
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
        System.out.println("用户：" + user_name + "，请求注册");
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
        return accountDao.checkUserExists(mapper,"userName",user_name);
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
        System.out.println("用户：" + user_name + "，请求注册");
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
        return accountDao.checkUserExists(mapper,"userName",user_name);
    }

    /**
     * 传入更改用户的用户ID以及用户的老密码和新密码，如果输入的老密码可以和用户的密码相匹配，就
     * 将更改数据库中用户的密码，如果不匹配就返回false
     * @param user_id 更改密码的用户的用户ID，类型为int
     * @param old_passwd 更改密码的用户的老密码，类型为String
     * @param new_passwd 更改密码的用户的新密码，类型为String
     * @return 返回true则成功更改密码
     * @exception UserNotExistException 用户不存在，无法更改密码
     * @exception UserFrozenException 用户被冻结，无法更改密码
     */
    public boolean userPasswdChanging(int user_id, String old_passwd, String new_passwd) {
        System.out.println("用户ID：" + user_id + "，请求更改密码");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        int id = accountDao.checkUserExists(mapper,"userID","" + user_id);
        if (id == -1) {
            throw new UserNotExistException();
        }
        if (accountDao.getUserIfFrozen(mapper,id)) {
            throw new UserFrozenException();
        }
        if (accountDao.checkUserPasswd(mapper,user_id,old_passwd) != user_id) {
            return false;
        }
        accountDao.updatePasswd(mapper,user_id,new_passwd);
        return true;
    }

    /**
     * 传入机构的ID，返回机构的所有信息，具体的Map格式看返回内容
     * @param agency_id 查询的机构的ID，类型为int
     * @return 返回机构的所有信息，agencyID，agencyName，agencyPasswd，agentName，
     * agentTel，agentEmail，所有均为String类型，如果机构不存在则返回null
     */
    public Map agencyInformation(int agency_id) {
        System.out.println("请求获得机构：" + agency_id + "的机构信息");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        return accountDao.getAgencyInformation(mapper,agency_id);
    }

    /**
     * 传入机构的ID，返回机构下的所有的用户的用户ID
     * @param agency_id 查询的机构的ID，类型为int
     * @return 返回所有用户的用户ID的List，类型为List<Integer>如果用户不存在则返回null
     */
    public List<Integer> agencyAllUser(int agency_id) {
        System.out.println("请求获得机构：" + agency_id + "的所有下属用户用户ID");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        return accountDao.getAgencyUsers(mapper,agency_id);
    }

    /**
     * 传入用户的ID，返回用户的所有信息，具体的Map格式看返回内容
     * @param user_id 查询的用户的ID，类型为int
     * @return 返回用户的所有信息，userID,userName,userPasswd,userTel,userEmail,
     * userIdentity,agency,availableBalance,frozenBalance,isFrozen，所有均为
     * String类型，如果用户不存在则返回null
     */
    public Map userInformation(int user_id) {
        System.out.println("请求获得用户：" + user_id + "的用户信息");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        return accountDao.getUserInformation(mapper,user_id);
    }

    /**
     * 传入冻结用户的用户ID和是否冻结变更数据库中帐号的冻结状态
     * @param user_id 冻结用户的用户ID，类型为int
     * @param if_frozen 是否冻结，类型为boolean
     * @return 如果成功冻结则返回1，如果成功解冻返回2
     * @exception UserNotExistException 不存在该ID的用户
     */
    public int freezeUnfreeze(int user_id, boolean if_frozen) {
        System.out.println(if_frozen?"请求冻结":"请求解冻" + "用户：" + user_id);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        if (accountDao.checkUserExists(mapper,"userID",""+user_id) == -1)
            throw new UserNotExistException();
        accountDao.updateFrozen(mapper,user_id, if_frozen);
        if (if_frozen)
            return 1;
        else
            return 2;
    }

    /**
     * 传入找回密码的用户的用户名身份证号以及新的密码，如果成功找回密码则返回true
     * @param user_name 找回密码的用户的用户名，类型为String
     * @param user_identity 找回密码的用户的身份证号，类型为String
     * @param new_passwd 找回密码的用户的新密码，类型为String
     * @return 如果成功查询到返回true
     * @exception UserNotExistException 用户不存在，无法找回密码
     * @exception UserFrozenException 用户被冻结，无法找回密码
     */
    public boolean foundPasswd(String user_name, String user_identity, String new_passwd) {
        System.out.println("用户：" + user_name + "，请求找回密码");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        int id = accountDao.checkUserExists(mapper,"userName",user_name);
        if (id == -1) {
            throw new UserNotExistException();
        } else if (accountDao.getUserIfFrozen(mapper,id)){
            throw new UserFrozenException();
        } else {
            accountDao.updatePasswd(mapper,id,new_passwd);
        }
        return true;
    }

    /**
     * 获取区块链中所有机构相关的交易信息，并且返回List<Map<String, String>，
     * 其中map的key为交易ID，map的value为交易信息
     * @param agency_id 查询的机构ID
     * @param start_date 查询的开始时间格式为yyyy-MM-dd HH:mm:ss
     * @param end_date 查询的结束时间格式为yyyy-MM-dd HH:mm:ss
     * @param trade_type 交易类型，0为充值，1为提现，2为转帐和消费
     * @return 返回一个listmap，其中map的key为交易ID，map的value为交易信息
     */
    public List<Map<String, String>> agencyTradeInformation(int agency_id, String start_date, String end_date, int trade_type) {
        System.out.println("请求获取机构：" + agency_id + "的从" + start_date + "到" + end_date + "的交易信息");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        List<String> idList = accountDao.getTransactionID(mapper,null,agency_id,start_date,end_date,trade_type);
        if (Main.blockChain) {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            if (trade_type == 2) {
                for (String i : idList) {
                    Map<String, String> map = new HashMap<String, String>();
                    String str = blockChainService.QueryTransaction(i);
                    map.put(i,str);
                    list.add(map);
                }
            } else {
                for (String i : idList) {
                    Map<String, String> map = new HashMap<String, String>();
                    String str = blockChainService.QueryBalanceChange(i);
                    map.put(i,str);
                    list.add(map);
                }
            }
            return list;
        }
        return null;
    }

    /**
     * 获取区块链中所有用户相关的交易信息，并且返回List<Map<String, String>，
     * 其中map的key为交易ID，map的value为交易信息
     * @param user_id 查询的用户ID
     * @param start_date 查询的开始时间格式为yyyy-MM-dd HH:mm:ss
     * @param end_date 查询的结束时间格式为yyyy-MM-dd HH:mm:ss
     * @param trade_type 交易类型，0为充值，1为提现，2为转帐和消费
     * @return 返回一个listmap，其中map的key为交易ID，map的value为交易信息
     */
    public List<Map<String, String>> userTradeInformation(int user_id, String start_date, String end_date, int trade_type) {
        System.out.println("请求获取用户：" + user_id + "的从" + start_date + "到" + end_date + "的交易信息");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        List<String> idList = accountDao.getTransactionID(mapper,user_id,null,start_date,end_date,trade_type);
        if (Main.blockChain) {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            if (trade_type == 2) {
                for (String i : idList) {
                    Map<String, String> map = new HashMap<String, String>();
                    String str = blockChainService.QueryTransaction(i);
                    map.put(i,str);
                    list.add(map);
                    String s = "make ide happy";
                }
            } else {
                for (String i : idList) {
                    Map<String, String> map = new HashMap<String, String>();
                    String str = blockChainService.QueryBalanceChange(i);
                    map.put(i,str);
                    list.add(map);
                    String s = "make ide happier";
                }
            }
            return list;
        }
        return null;
    }

    /**
     * 转帐消费函数，传入支付方和收款方以及金额，如果支付方和收款方有一方为平台账
     * 户或者清洁算账户则不进行交易记录，如果说双方均为普通用户，则首先根据用户的
     * 余额判断是否可以进行本次交易以及账户是否被冻结，如果没问题则根据时间和交易
     * 信息生成交易ID然后将交易记录插入数据库以及区块链，如果是消费则调用清算平台
     * 的消费接口，清算平台将会调用本方法，并且扣除支付方金额到清算账户，如果是转
     * 帐则扣除支付方余额增加收款方余额
     * 清算平台在清算过程中会将清算账户中的余额转到各个收款方账户中，并且将最终的
     * 手续费转到平台余额中
     * @param pay_user_id 支付方用户ID，其中1为平台账户，2为清洁算账户
     * @param get_user_id 收款方用户ID，其中1为平台账户，2为清洁算账户
     * @param amount 支付金额
     * @param trade_type 支付类型，false为转帐，true为消费
     * @return 如果用户余额及收款方冻结状态有问题则返回false，如果消费或者转帐成功则
     * 返回true
     * @exception UserNotExistException 用户不存在，无法转帐或消费
     * @exception UserFrozenException 用户被冻结，无法转帐或消费
     */
    public boolean transferConsume(int pay_user_id, int get_user_id, double amount, boolean trade_type) {
        String type = trade_type?"消费":"转帐";
        System.out.println("请求一笔由" + pay_user_id + "支付给" + get_user_id + "的" + type);
        SqlSession sqlSession = sqlSessionFactory.openSession();
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
                throw new UserNotExistException();
            } else if (((BigDecimal) map1.get("availableBalance")).compareTo(new BigDecimal(amount)) == -1 && (Boolean) map2.get("ifFrozen")) {
                return false;
            } else if ((Boolean) map1.get("ifFrozen")) {
                throw new UserFrozenException();
            }
            //生成消费ID
            int agencyid1 = (Integer) map1.get("agency");
            int agencyid2 = (Integer) map2.get("agency");
            StringBuilder s = new StringBuilder();
            s.append(2);
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
            accountDao.insertTransaction(mapper,s.toString(),2,datetime,agencyid1,pay_user_id,agencyid2,get_user_id,amount);
            if (Main.blockChain) {
                blockChainService.InsertTransaction(s.toString(),agencyid1,pay_user_id,agencyid2,get_user_id,datetime,trade_type,amount);
            }
        }
        return true;
    }

    /**
     * 充值接口，传入用户id金额以及充值平台，如果用户不存在则丢出异常，如果用户的
     * 账户被冻结则返回false，没有问题则根据时间和交易信息生成交易ID，并将交易计
     * 入数据库和区块链，最后调用清算平台的充值接口并返回true
     * @param user_id 充值的用户ID
     * @param amount 充值金额
     * @param recharge_platform 充值平台，false为微信，true为支付宝
     * @return 如果用户帐户被冻结则返回false，如果充值成功则返回true
     * @exception UserNotExistException 用户不存在，无法充值
     */
    public boolean reCharge(int user_id, double amount, boolean recharge_platform) {
        String type = recharge_platform?"支付宝":"微信";
        System.out.println("请求一笔由" + user_id + "发起的" + type + "充值，金额：" + amount);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        //获取用户信息
        Map map = accountDao.getUserInformation(mapper,user_id);
        //判断用户是否存在
        if (map == null) {
            throw new UserNotExistException();
        } else if ((Boolean) map.get("ifFrozen")){
            return false;
        }
        //生成交易ID
        int agencyid = (Integer) map.get("agency");
        StringBuilder s = new StringBuilder();
        s.append(0);
        s.append(agencyid).append(0);
        s.append(user_id);
        //增加用户余额并且在加入交易数据库后入链
        accountDao.addBalance(mapper,user_id,amount);
        String datetime = generatorID(s);
        accountDao.insertTransaction(mapper,s.toString(),0,datetime,null,null,agencyid,user_id,amount);
        if (Main.blockChain) {
            blockChainService.InsertBalanceChange(s.toString(),agencyid,user_id,datetime,recharge_platform,amount);
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
    }

    /**
     * 提现接口，传入用户id金额以及提现平台，如果用户不存在则丢出异常，如果用户的
     * 账户被冻结则返回false，没有问题则根据时间和交易信息生成交易ID，并将交易计
     * 入数据库和区块链，最后调用清算平台的充值接口并返回true
     * @param user_id 充值的用户ID
     * @param amount 充值金额
     * @param draw_platform 提现平台，false为微信，true为支付宝
     * @return 如果用户帐户被冻结则返回false，如果充值成功则返回true
     * @exception UserNotExistException 用户不存在，无法充值
     */
    public boolean drawMoney(int user_id, double amount, boolean draw_platform) {
        String type = draw_platform?"支付宝":"微信";
        System.out.println("请求一笔由" + user_id + "发起的" + type + "提现，金额：" + amount);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        //获取用户信息
        Map map = accountDao.getUserInformation(mapper,user_id);
        //判断用户是否存在
        if (map == null) {
            throw new UserNotExistException();
        } else if (((BigDecimal) map.get("availableBalance")).compareTo(new BigDecimal(amount)) == -1 && (Integer) map.get("ifFrozen") == 1) {
            return false;
        }
        //生成交易ID
        int agencyid = (Integer) map.get("agency");
        StringBuilder s = new StringBuilder();
        s.append(1);
        s.append(agencyid).append(0);
        s.append(user_id);
        //扣除用户余额并且在加入交易数据库后入链
        accountDao.minusBalance(mapper,user_id,amount);
        String datetime = generatorID(s);
        accountDao.insertTransaction(mapper,s.toString(),1,datetime,agencyid,user_id,null,null,amount);
        if (Main.blockChain) {
            blockChainService.InsertBalanceChange(s.toString(),agencyid,user_id,datetime,draw_platform,amount);
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
