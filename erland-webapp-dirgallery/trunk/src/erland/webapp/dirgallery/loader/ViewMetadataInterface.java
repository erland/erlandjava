package erland.webapp.dirgallery.loader;

public interface ViewMetadataInterface {
    String[] getMetadataNames();

    String getMetadataValue(String name);

    String getMetadataDescription(String name);
}