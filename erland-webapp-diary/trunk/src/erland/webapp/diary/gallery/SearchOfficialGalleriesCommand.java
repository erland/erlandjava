package erland.webapp.diary.gallery;

public class SearchOfficialGalleriesCommand extends SearchGalleriesCommand {
    protected String getQueryFilter() {
        return "allofficialforuser";
    }
}
