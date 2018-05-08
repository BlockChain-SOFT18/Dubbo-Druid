package jj.druid.provider;

import jj.druid.mybatis.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.Map;

public class AccountDao {

    public int checkAgencyExists(Mapper mapper,String key,String value) {
        Map map = new HashMap();
        map.put(key,value);
        int id = mapper.selectAgencyID(map);
        return id;
    }

    public int checkUserExists(Mapper mapper,String key,String value) {
        Map map = new HashMap();
        map.put(key,value);
        int id = mapper.selectUserID(map);
        return id;
    }

    public int checkUserAgencyDuplicate(Mapper mapper,String user_identity,int agency) {
        Map map = new HashMap();
        map.put("userIdentify",user_identity);
        map.put("agency",agency);
        int id = mapper.selectAgencyID(map);
        return id;
    }

    public int checkUserPasswd(Mapper mapper,String user_name,String passwd) {
        Map map = new HashMap();
        map.put("userName",user_name);
        map.put("userPasswd",passwd);
        int id = mapper.selectUserID(map);
        return id;
    }

    public int checkUserPasswd(Mapper mapper,int user_id,String passwd) {
        Map map = new HashMap();
        map.put("userID",user_id);
        map.put("userPasswd",passwd);
        int id = mapper.selectUserID(map);
        return id;
    }

    public int checkAgencyPasswd(Mapper mapper,String agency_name,String passwd) {
        Map map = new HashMap();
        map.put("agencyName",agency_name);
        map.put("agencyPasswd",passwd);
        int id = mapper.selectAgencyID(map);
        return id;
    }

    public int checkAgencyPasswd(Mapper mapper,int agency_id,String passwd) {
        Map map = new HashMap();
        map.put("agencyID",agency_id);
        map.put("agencyPasswd",passwd);
        int id = mapper.selectAgencyID(map);
        return id;
    }

    public double getUserBalance(Mapper mapper,int user_id) {
        double balance = mapper.selectUserAvailableBalance(user_id);
        return balance;
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

    void updatePasswd(Mapper mapper,int user_id,String new_passwd) {
        mapper.updateUserPasswd(user_id, new_passwd);
    }
}
