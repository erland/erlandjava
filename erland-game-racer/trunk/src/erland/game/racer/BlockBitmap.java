package erland.game.racer;

import erland.game.BlockContainerInterface;
import erland.game.MapEditorBlockInterface;
import erland.game.GameEnvironmentInterface;
import erland.util.*;

import java.awt.*;
import java.awt.image.VolatileImage;

class BlockBitmap extends Block {
    protected Image image;
    protected String imageName;
    protected SubImageHandler subImages;
    public int subImage;

    public void setImage(Image image, int subImage)
    {
        this.imageName = "";
        this.subImage = subImage;
        this.image = image;
        this.subImages = new SubImageHandler(this.image,32,32,8,8);
    }
    public void setImage(String image, int subImage)
    {
        setImage(environment.getImageHandler().getImage(image),subImage);
        this.imageName = image;
    }

    public void draw(Graphics g,int level)
    {
        if(level==0) {
            //g.drawImage(image,cont.getDrawingPositionX(posX),cont.getDrawingPositionY(posY),null);
            if(subImages!=null) {
                subImages.drawImage(g,subImage,cont.getDrawingPositionX(posX),cont.getDrawingPositionY(posY));
            }
        }
    }

    public void write(ParameterValueStorageInterface out) {
        super.write(out);
        out.setParameter("image",imageName);
        out.setParameter("subimage",Integer.toString(subImage));
    }

    public void read(ParameterValueStorageInterface in) {
        super.read(in);
        String image = in.getParameter("image");
        int subImage = Integer.valueOf(in.getParameter("subimage")).intValue();
        setImage(image,subImage);
    }
}
