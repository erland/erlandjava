package erland.webapp.gallery.gallery.picture;

public class AdvancedSearchOfficialPicturesCommand extends AdvancedSearchPicturesCommand {
    protected String getAllFilter() {
        return "allofficialforgallerybetweendates";
    }

    protected String getCategoryTreeFilter() {
        if(!getAllCategories().booleanValue()) {
            return "allofficialforgalleryandcategorylistbetweendates";
        }else {
            return "allofficialforgalleryandcategorylistallrequiredbetweendates";
        }
    }
}