package erland.webapp.diary.account;

import erland.webapp.usermgmt.User;

public interface ViewUserAccountsInterface {
    UserAccount[] getAccounts();
    User getUser(UserAccount account);
}
