package erland.webapp.diary.diary;

public class SearchOfficialDiariesCommand extends SearchDiariesCommand {
    protected String getQueryFilter() {
        return "allofficialforuser";
    }
}
