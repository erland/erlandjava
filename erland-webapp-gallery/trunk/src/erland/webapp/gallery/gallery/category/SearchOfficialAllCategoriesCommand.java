package erland.webapp.gallery.gallery.category;

public class SearchOfficialAllCategoriesCommand extends SearchCategoriesCommand{
    protected String getParentFilter() {
        return "allofficialforgalleryorderedbyname";
    }

    protected String getNoParentFilter() {
        return "allofficialforgalleryorderedbyname";
    }
}