package erland.webapp.gallery.gallery.category;

public class SearchAllCategoriesCommand extends SearchCategoriesCommand{
    protected String getNoParentFilter() {
        return "allforgalleryorderedbyname";
    }

    protected String getParentFilter() {
        return "allforgalleryorderedbyname";
    }
}