package erland.webapp.dirgallery.gallery;

public class SearchOfficialFriendGalleriesCommand extends SearchFriendGalleriesCommand {
    protected String getGalleryListQuery() {
        return "allofficialforgallerylist";
    }
}
