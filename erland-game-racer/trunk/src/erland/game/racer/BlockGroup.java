package erland.game.racer;

import erland.util.ParameterValueStorageExInterface;

/**
 * Class that implements a block that can be drawn as
 * a {@link BlockBitmap} but also has information about which
 * type of block it is
 * @author Erland Isaksson
 */
public class BlockGroup extends BlockBitmap {
    private int blockType;

    /**
     * Get block type of the block
     * @return The block type of the block
     */
    public int getBlockType()
    {
        return blockType;
    }
    /**
     * Set the block type of the block
     * @param blockType The block type
     */
    public void setBlockType(int blockType)
    {
        this.blockType = blockType;
    }

    public void write(ParameterValueStorageExInterface out) {
        out.setParameter("x",Integer.toString(getPosX()));
        out.setParameter("y",Integer.toString(getPosY()));
        out.setParameter("blocktype",Integer.toString(blockType));
    }

    public void read(ParameterValueStorageExInterface in) {
        int x = Integer.valueOf(in.getParameter("x")).intValue();
        int y = Integer.valueOf(in.getParameter("y")).intValue();
        blockType = Integer.valueOf(in.getParameter("blocktype")).intValue();
        setPos(x,y);
    }
}
