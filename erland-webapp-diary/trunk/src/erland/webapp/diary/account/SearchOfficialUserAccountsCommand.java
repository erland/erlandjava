package erland.webapp.diary.account;

public class SearchOfficialUserAccountsCommand extends SearchUserAccountsCommand {
    protected String getQueryFilter() {
        return "allofficial";
    }
}
