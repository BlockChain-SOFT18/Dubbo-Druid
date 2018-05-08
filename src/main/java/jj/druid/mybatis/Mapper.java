package jj.druid.mybatis;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface Mapper {
    int selectAgencyID(Map map);
    int selectUserID(Map map);
    double selectUserAvailableBalance(@Param("userID")int userID);
    void insertUser(@Param("userName") String user_name,
                    @Param("userPasswd") String user_passwd,
                    @Param("userRealName") String user_realname,
                    @Param("userTel") String user_tel,
                    @Param("userEmail") String user_email,
                    @Param("userIdentity") String user_identity,
                    @Param("agency") int under_agency_id);
    void updateUserPasswd(@Param("userID") int user_id, @Param("userPasswd") String new_passwd);
    Map selectUser(@Param("userID") int user_id);
    Map selectAgency(@Param("agencyID") int agency_id);
    List<Integer> selectAgencyUser(@Param("agency") int agency_id);
    int selectUserIdentity(@Param("userName") String user_name, @Param("userIdentity") String user_identity);
    void updateIsFrozen(@Param("userID") int user_id, @Param("isFrozen") boolean is_frozen);
    void changeBalance(@Param("userID") int user_id, @Param("amount") double amount);
}
