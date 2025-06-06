package Service;

import DAO.AccountDao;
import Model.Account;

public class AccountService {
    private final AccountDao accountDao;

    public AccountService() {
        this.accountDao = new AccountDao();
    }

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    //Handles user registration.

    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return null;
        }
        if (accountDao.getAccountByUsername(account.getUsername()) != null) {
            return null;
        }
        return accountDao.createAccount(account);
    }

    //Handles user login.

    public Account loginAccount(Account account) {
        Account existingAccount = accountDao.getAccountByUsername(account.getUsername());
        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            return existingAccount;
        }
        return null;
    }

    //Retrieves an account by its ID.

    public Account getAccountById(int accountId) {
        return accountDao.getAccountById(accountId);
    }
}