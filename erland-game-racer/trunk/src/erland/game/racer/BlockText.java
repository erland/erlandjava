package erland.game.racer;



import erland.util.ParameterValueStorageInterface;
import erland.util.Log;

import java.awt.*;

public class BlockText extends Block {
    protected String text;
    protected Block backgroundBlock;
    protected Color color = Color.white;

    public void setBackgroundBlock(Block block)
    {
        backgroundBlock = block;
    }
    public Block getBackgroundBlock()
    {
        return backgroundBlock;
    }
    public void setText(String text)
    {
        this.text= text;
    }
    public String getText()
    {
        return text;
    }
    public void setColor(Color c)
    {
        this.color = c;
    }

    public void draw(Graphics g,int level)
    {
        if(backgroundBlock!=null) {
            backgroundBlock.draw(g,level);
        }
        Color old = g.getColor();
        g.setColor(color);
        g.drawString(text,cont.getDrawingPositionX(posX)+10,cont.getDrawingPositionY(posY)+cont.getSquareSize()-10);
        g.setColor(old);
    }
    public void write(ParameterValueStorageInterface out) {
        super.write(out);
        out.setParameter("text",text);
    }

    public void read(ParameterValueStorageInterface in) {
        super.read(in);
        text = in.getParameter("text");
        backgroundBlock = null;
    }
}
