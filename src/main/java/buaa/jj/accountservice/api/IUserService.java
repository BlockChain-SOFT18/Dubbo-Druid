package buaa.jj.accountservice.api;

import java.util.Date;
public interface IUserService {
    public int Recharge(int UserID,String RequestTime,String RequestID,
                        float Money,boolean Way,boolean RequestState);
    public int Withdraw(int UserID,String RequestTime,String RequestID,
                        float Money,boolean Way,boolean RequestState);
    public int Consume(int UserID,int MerchantId,String RequestTime,String RequestID,
                       float Money,boolean RequestState);
}

