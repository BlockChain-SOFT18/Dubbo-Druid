package jj.druid.provider;

import jj.druid.api.AccountService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = {Exception.class, RuntimeException.class})
    public void exampleFunction() {
        accountDao.func();
        int i = 10/0;
        accountDao.function();
    }
}
