package erland.webapp.gallery.gallery;

public interface GalleryInterface {
    Integer getId();

    void setId(Integer id);

    String getUsername();

    void setUsername(String username);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    Boolean getOfficial();

    void setOfficial(Boolean official);

    Integer getTopCategory();

    void setTopCategory(Integer topCategory);

    Integer getReferencedGallery();

    void setReferencedGallery(Integer referencedGallery);
}