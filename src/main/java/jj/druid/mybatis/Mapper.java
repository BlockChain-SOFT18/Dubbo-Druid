package jj.druid.mybatis;

import org.apache.ibatis.annotations.Param;

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
}
