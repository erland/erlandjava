package erland.game;

import erland.util.ImageHandlerInterface;
import erland.util.ImageCreatorInterface;
import erland.util.ParameterValueStorageInterface;

public interface GameEnvironmentInterface {
    public ImageHandlerInterface getImageHandler();
    public ImageCreatorInterface getImageCreator();
    public ScreenHandlerInterface getScreenHandler();
    public ParameterValueStorageInterface getStorage();
}
