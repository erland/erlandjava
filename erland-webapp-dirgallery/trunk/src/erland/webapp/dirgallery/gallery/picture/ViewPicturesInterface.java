package erland.webapp.dirgallery.gallery.picture;

public interface ViewPicturesInterface {
    Picture[] getPictures();

    String getPictureComment(Picture picture);
}
