package buaa.jj.accountservice.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cache.Cache;

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
    Boolean selectUserIfFrozen(@Param("userID") int user_id);
    Boolean selectUserIfFrozen(@Param("userName") String user_name);
    void updateIfFrozen(@Param("userID") int user_id, @Param("ifFrozen") boolean if_frozen);
    void changeBalance(@Param("userID") int user_id, @Param("amount") double amount);
    void updatePlatformBalance(@Param("amount") double amount);
    void updateLiquidationBalance(@Param("amount") double amount);
    void insertTransaction(@Param("transactionID") String transactionID,
                           @Param("transactionType") int transactionType,
                           @Param("transactionDate") String transactionDate,
                           @Param("payerAgencyID") Integer payerAgencyID,
                           @Param("payerUserID") Integer payerUserID,
                           @Param("receiverAgencyID") Integer receiverAgencyID,
                           @Param("receiverUserID") Integer receiverUserID,
                           @Param("transactionMoney") double transactionMoney);
    List<String> selectTransactionID(@Param("userID") Integer userID,
                                      @Param("agencyID") Integer agencyID,
                                      @Param("startTime") String startTime,
                                      @Param("endTime") String endTime,
                                      @Param("state") int state);
}
