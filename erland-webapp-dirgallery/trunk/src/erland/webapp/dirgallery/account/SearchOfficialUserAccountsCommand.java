package erland.webapp.dirgallery.account;

public class SearchOfficialUserAccountsCommand extends SearchUserAccountsCommand {
    protected String getQueryFilter() {
        return "allofficial";
    }
}
