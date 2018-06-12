# 虚拟账户
## 环境部署
* zookeeper
* redis
* tomcat
* jdk
## 接口
###### userLogin
```java
/**
* 传入用户的用户名和密码然后在数据库中进行搜索，如果能找到相匹配的用户就返回用户的用户ID
* 如果没有找到相匹配的用户就返回-1
* @param user_name 登录的用户的用户名，类型为String
* @param user_passwd 登录的用户的密码，类型为String
* @return 返回用户的ID，如果没有查询到相匹配的用户就返回-1，类型为int
* @exception UserNotExistException 用户不存在，无法登录
* @exception UserFrozenException 用户被冻结，无法登录
*/
public int userLogin(String user_name, String user_passwd);
```
###### agencyLogin
```java
/**
* 传入机构的用户名和密码然后在数据库中进行搜索，如果能找到相匹配的机构就返回机构的机构ID
* 如果没有找到相匹配的机构就返回-1
* @param agency_name 登录的机构的用户名，类型为String
* @param agency_passwd 登录的机构的密码，类型为String
* @return 返回机构的ID，如果没有查询到相匹配的用户就返回-1，类型为int
*/
public int agencyLogin(String agency_name, String agency_passwd);
```
###### userRegister
```java
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
                        int under_agency_id);
```
```java
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
                        String under_agency_name);
```
###### userPasswdChanging
```java
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
public boolean userPasswdChanging(int user_id, String old_passwd, String new_passwd);
```
###### agencyInformation
```java
/**
* 传入机构的ID，返回机构的所有信息，具体的Map格式看返回内容
* @param agency_id 查询的机构的ID，类型为int
* @return 返回机构的所有信息，agencyID，agencyName，agencyPasswd，agentName，
* agentTel，agentEmail，所有均为String类型，如果机构不存在则返回null
*/
public Map agencyInformation(int agency_id);
```
###### agencyAllUser
```java
/**
* 传入机构的ID，返回机构下的所有的用户的用户ID
* @param agency_id 查询的机构的ID，类型为int
* @return 返回所有用户的用户ID的List，类型为List<Integer>如果用户不存在则返回null
*/
public List<Integer> agencyAllUser;
```
###### userInformation
```java
/**
* 传入用户的ID，返回用户的所有信息，具体的Map格式看返回内容
* @param user_id 查询的用户的ID，类型为int
* @return 返回用户的所有信息，userID,userName,userPasswd,userTel,userEmail,
* userIdentity,agency,availableBalance,frozenBalance,isFrozen，所有均为
* String类型，如果用户不存在则返回null
*/
public Map userInformation(int user_id);
```
###### freezeUnfreeze
```java
/**
* 传入冻结用户的用户ID和是否冻结变更数据库中帐号的冻结状态
* @param user_id 冻结用户的用户ID，类型为int
* @param if_frozen 是否冻结，类型为boolean
* @return 如果成功冻结则返回1，如果成功解冻返回2
* @exception UserNotExistException 不存在该ID的用户
*/
public int freezeUnfreeze(int user_id, boolean if_frozen);
```
###### foundPasswd
```java
/**
* 传入找回密码的用户的用户名身份证号以及新的密码，如果成功找回密码则返回true，
* 如果用户名和用户身份验证不一致则返回false
* @param user_name 找回密码的用户的用户名，类型为String
* @param user_identity 找回密码的用户的身份证号，类型为String
* @param new_passwd 找回密码的用户的新密码，类型为String
* @return 如果成功找回密码则返回true，如果用户名和用户身份验证不一致则返回false
* @exception UserNotExistException 用户不存在，无法找回密码
* @exception UserFrozenException 用户被冻结，无法找回密码
*/
public boolean foundPasswd(String user_name, String user_identity, String new_passwd);
```
###### agencyTradeInformation
```java
/**
* 获取区块链中所有机构相关的交易信息，并且返回List<Map<String, String>，
* 其中map的key为交易ID，map的value为交易信息
* @param agency_id 查询的机构ID
* @param start_date 查询的开始时间格式为yyyy-MM-dd HH:mm:ss
* @param end_date 查询的结束时间格式为yyyy-MM-dd HH:mm:ss
* @param trade_type 交易类型，0为充值，1为提现，2为转帐和消费
* @return 返回一个listmap，其中map的key为交易ID，map的value为交易信息
*/
public List<Map<String, String>> agencyTradeInformation(int agency_id, String start_date, String end_date, int trade_type);
```
###### userTradeInformation
```java
/**
* 获取区块链中所有用户相关的交易信息，并且返回List<Map<String, String>，
* 其中map的key为交易ID，map的value为交易信息
* @param user_id 查询的用户ID
* @param start_date 查询的开始时间格式为yyyy-MM-dd HH:mm:ss
* @param end_date 查询的结束时间格式为yyyy-MM-dd HH:mm:ss
* @param trade_type 交易类型，0为充值，1为提现，2为转帐和消费
* @return 返回一个listmap，其中map的key为交易ID，map的value为交易信息
*/
public List<Map<String, String>> userTradeInformation(int user_id, String start_date, String end_date, int trade_type);
```
###### transferConsume
```java
/**
* 转帐消费函数，传入支付方和收款方以及金额，如果支付方和收款方有一方为平台账
* 户或者清洁算账户则不进行交易记录，如果说双方均为普通用户，则首先根据用户的
* 余额判断是否可以进行本次交易以及账户是否被冻结，如果没问题则根据时间和交易
* 信息生成交易ID然后将交易记录插入数据库以及区块链，如果是消费则扣除支付方金
* 额到清算账户并调用清算平台的消费接口，如果是转帐则扣除支付方余额增加收款方
* 余额，清算平台在清算过程中会将清算账户中的余额转到各个收款方账户中，并且将
* 最终的手续费转到平台余额中
* @param pay_user_id 支付方用户ID，其中1为平台账户，2为清洁算账户
* @param get_user_id 收款方用户ID，其中1为平台账户，2为清洁算账户
* @param amount 支付金额
* @param trade_type 支付类型，false为转帐，true为消费
* @return 如果用户余额及收款方冻结状态有问题则返回false，如果消费或者转帐成功则
* 返回true
* @exception UserNotExistException 用户不存在，无法转帐或消费
* @exception UserFrozenException 用户被冻结，无法转帐或消费
*/
public boolean transferConsume(int pay_user_id, int get_user_id, double amount, boolean trade_type);
```
###### reCharge
```java
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
public boolean reCharge(int user_id, double amount, boolean recharge_platform);
```
###### drawMoney
```java
/**
* 提现接口，传入用户id金额以及提现平台，如果用户不存在或被冻结则丢出异常，
* 账户余额不足则返回false，没有问题则根据时间和交易信息生成交易ID，并将交
* 易计入数据库和区块链，最后调用清算平台的提现接口并返回true
* @param user_id 提现的用户ID
* @param amount 提现金额
* @param draw_platform 提现平台，false为微信，true为支付宝
* @return 如果用户帐户余额不足则返回false，如果提现成功则返回true
* @exception UserNotExistException 用户不存在，无法提现
* @exception UserFrozenException 用户被冻结，无法提现
*/
public boolean drawMoney(int user_id, double amount, boolean draw_platform);
```