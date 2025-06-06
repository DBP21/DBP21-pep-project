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

    /**
     * Handles user registration.
     *
     * @param account The account to register.
     * @return The registered account if successful, otherwise null.
     */
    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return null;
        }
        if (accountDao.getAccountByUsername(account.getUsername()) != null) {
            return null;
        }
        return accountDao.createAccount(account);
    }

    /**
     * Handles user login.
     *
     * @param account The account credentials to check.
     * @return The authenticated account if successful, otherwise null.
     */
    public Account loginAccount(Account account) {
        Account existingAccount = accountDao.getAccountByUsername(account.getUsername());
        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            return existingAccount;
        }
        return null;
    }

     /**
     * Retrieves an account by its ID.
     *
     * @param accountId The ID of the account to retrieve.
     * @return The Account object if found, otherwise null.
     */
    public Account getAccountById(int accountId) {
        return accountDao.getAccountById(accountId);
    }
}
