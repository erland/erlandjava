package erland.webapp.diary.gallery;

import erland.webapp.common.CommandInterface;
import erland.webapp.common.WebAppEnvironmentInterface;

import javax.servlet.http.HttpServletRequest;

public class EditGalleryEntryCommand implements CommandInterface, ViewGalleryEntryInterface {
    private WebAppEnvironmentInterface environment;
    private GalleryEntry entry;

    public void init(WebAppEnvironmentInterface environment) {
        this.environment = environment;
    }

    public String execute(HttpServletRequest request) {
        String gallery = request.getParameter("gallery");
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String image = request.getParameter("image");
        String link = request.getParameter("link");
        entry = (GalleryEntry) environment.getEntityFactory().create("diarygalleryentry");
        if(id!=null && id.length()>0) {
            entry.setId(Integer.valueOf(id));
        }
        entry.setGallery(Integer.valueOf(gallery));
        entry.setTitle(title);
        entry.setDescription(description);
        entry.setImage(image);
        entry.setLink(link);
        environment.getEntityStorageFactory().getStorage("diarygalleryentry").store(entry);
        return null;
    }

    public GalleryEntry getEntry() {
        return entry;
    }
}
