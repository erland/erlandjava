package erland.webapp.gallery.gallery.picture;

public class SearchOfficialPicturesCommand extends SearchPicturesCommand {
    protected String getAllFilter() {
        return "allofficialforgallery";
    }

    protected String getCategoryTreeFilter() {
        return "allofficialforgalleryandcategorylist";
    }
}