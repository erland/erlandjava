package erland.webapp.gallery.gallery.category;

public class SearchOfficialCategoriesCommand extends SearchCategoriesCommand {
    protected String getNoParentFilter() {
        return "allofficialforgallerywithoutparent";
    }

    protected String getParentFilter() {
        return "allofficialforgallerywithparent";
    }
}