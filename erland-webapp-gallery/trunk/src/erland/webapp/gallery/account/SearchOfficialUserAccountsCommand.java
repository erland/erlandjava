package erland.webapp.gallery.account;

public class SearchOfficialUserAccountsCommand extends SearchUserAccountsCommand {
    protected String getQueryFilter() {
        return "allofficial";
    }
}
