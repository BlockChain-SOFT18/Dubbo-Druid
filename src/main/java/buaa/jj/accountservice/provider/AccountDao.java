package buaa.jj.accountservice.provider;

import buaa.jj.accountservice.mybatis.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDao {

    public int checkAgencyExists(Mapper mapper, String key, String value) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(key,value);
        return mapper.selectAgencyID(map);
    }

    public int checkUserExists(Mapper mapper, String key, String value) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(key,value);
        return mapper.selectUserID(map);
    }

    public int checkUserAgencyDuplicate(Mapper mapper, String user_identity, int agency) {
        Map<String, java.io.Serializable> map = new HashMap<String, java.io.Serializable>();
        map.put("userIdentity",user_identity);
        map.put("agency",agency);
        return mapper.selectUserID(map);
    }

    public int checkUserPasswd(Mapper mapper, String user_name, String passwd) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName",user_name);
        map.put("userPasswd",passwd);
        return mapper.selectUserID(map);
    }

    public int checkUserPasswd(Mapper mapper, int user_id, String passwd) {
        Map<String, java.io.Serializable> map = new HashMap<String, java.io.Serializable>();
        map.put("userID",user_id);
        map.put("userPasswd",passwd);
        return mapper.selectUserID(map);
    }

    public int checkAgencyPasswd(Mapper mapper, String agency_name, String passwd) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("agencyName",agency_name);
        map.put("agencyPasswd",passwd);
        return mapper.selectAgencyID(map);
    }

    public int checkAgencyPasswd(Mapper mapper, int agency_id, String passwd) {
        Map<String, java.io.Serializable> map = new HashMap<String, java.io.Serializable>();
        map.put("agencyID",agency_id);
        map.put("agencyPasswd",passwd);
        return mapper.selectAgencyID(map);
    }

    public double getUserBalance(Mapper mapper, int user_id) {
        return mapper.selectUserAvailableBalance(user_id);
    }

    public void userInsert(Mapper mapper,
                           String user_name,
                           String user_passwd,
                           String user_realname,
                           String user_tel,
                           String user_email,
                           String user_identity,
                           int under_agency_id) {
        mapper.insertUser(user_name, user_passwd, user_realname, user_tel, user_email, user_identity, under_agency_id);
    }

    public void updatePasswd(Mapper mapper, int user_id, String new_passwd) {
        mapper.updateUserPasswd(user_id, new_passwd);
    }

    public Map getUserInformation(Mapper mapper, int user_id) {
        return mapper.selectUser(user_id);
    }

    public Map getAgencyInformation(Mapper mapper, int agency_id) {
        return mapper.selectAgency(agency_id);
    }

    public List<Integer> getAgencyUsers(Mapper mapper, int agency_id) {
        return mapper.selectAgencyUser(agency_id);
    }

    public boolean checkUserIdentity(Mapper mapper, String user_name, String user_identity) {
        return mapper.selectUserIdentity(user_name, user_identity) != -1;
    }

    public void updateFrozen(Mapper mapper, int user_id, boolean is_frozen) {
        mapper.updateIsFrozen(user_id,is_frozen);
    }

    public void insertTransaction(Mapper mapper,String transactionID,
                                  int transactionType,
                                  String transactionDate,
                                  Integer payerAgencyID,
                                  Integer payerUserID,
                                  Integer receiverAgencyID,
                                  Integer receiverUserID,
                                  double transactionMoney) {
        mapper.insertTransaction(transactionID, transactionType, transactionDate, payerAgencyID, payerUserID, receiverAgencyID, receiverUserID, transactionMoney);
    }

    public List<Integer> getTransactionID(Mapper mapper,Integer userID,
                                          Integer agencyID,
                                          String startTime,
                                          String endTime) {
        return mapper.selectTransactionID(userID, agencyID, startTime, endTime);
    }

    public void addBalance(Mapper mapper, int user_id,double amount) {
        mapper.changeBalance(user_id, amount);
    }

    public void minusBalance(Mapper mapper, int user_id,double amount) {
        mapper.changeBalance(user_id, -amount);
    }

    public void addPlatformBalance(Mapper mapper, double amount) {
        mapper.updatePlatformBalance(amount);
    }

    public void minusPlatformBalance(Mapper mapper, double amount) {
        mapper.updatePlatformBalance(-amount);
    }

    public void addLiquidationBalance(Mapper mapper, double amount) {
        mapper.updateLiquidationBalance(amount);
    }

    public void minusLiquidationBalance(Mapper mapper, double amount) {
        mapper.updateLiquidationBalance(-amount);
    }
}
