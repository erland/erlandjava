package erland.webapp.diary.account;

import erland.webapp.usermgmt.User;
import erland.webapp.diary.diary.Diary;

public interface ViewUserAccountInterface {
    UserAccount getAccount();
    User getUser();
    Diary[] getDiaries();
}
