package erland.webapp.gallery.gallery;

public class SearchOfficialGalleriesCommand extends SearchGalleriesCommand {
    protected String getQueryFilter() {
        return "allofficialforuser";
    }
}
