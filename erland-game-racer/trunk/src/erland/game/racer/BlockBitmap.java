package erland.game.racer;

import erland.util.*;
import java.awt.*;

/**
 * Class that implements a block that shows a bitmap
 * @author Erland Isaksson
 */
class BlockBitmap extends Block {
    /** The image which represents the background bitmap of the block */
    private Image image;
    /** The name of the image for the background bitmap */
    private String imageName;
    /** The sub image handler object */
    private SubImageHandler subImages;
    /** The sub image number of the image that should represent the background bitmap */
    private int subImage;

    /**
     * Set the background image
     * @param image The main image
     * @param subImage The sub image number of the sub image to use
     */
    public void setImage(Image image, int subImage)
    {
        this.imageName = "";
        this.subImage = subImage;
        this.image = image;
        this.subImages = new SubImageHandler(this.image,32,32,8,8);
    }
    /**
     * Set the background image
     * @param image The name of the main image
     * @param subImage The sub image number of the sub image to use
     */
    public void setImage(String image, int subImage)
    {
        if(getEnvironment().getImageHandler()!=null) {
            setImage(getEnvironment().getImageHandler().getImage(image),subImage);
        }
        this.imageName = image;
    }

    public void draw(Graphics g,int level)
    {
        if(level==0) {
            //g.drawImage(image,cont.getDrawingPositionX(posX),cont.getDrawingPositionY(posY),null);
            if(subImages!=null) {
                subImages.drawImage(g,subImage,getContainer().getDrawingPositionX(getPosX()),getContainer().getDrawingPositionY(getPosY()));
            }
        }
    }

    public void write(ParameterValueStorageExInterface out) {
        super.write(out);
        out.setParameter("image",imageName);
        out.setParameter("subimage",Integer.toString(subImage));
    }

    public void read(ParameterValueStorageExInterface in) {
        super.read(in);
        String image = in.getParameter("image");
        int subImage = Integer.valueOf(in.getParameter("subimage")).intValue();
        setImage(image,subImage);
    }
}
