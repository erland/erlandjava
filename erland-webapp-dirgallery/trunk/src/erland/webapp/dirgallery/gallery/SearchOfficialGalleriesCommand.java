package erland.webapp.dirgallery.gallery;


public class SearchOfficialGalleriesCommand extends SearchGalleriesCommand {
    protected String getQueryFilter() {
        return "allofficialforuser";
    }
}
