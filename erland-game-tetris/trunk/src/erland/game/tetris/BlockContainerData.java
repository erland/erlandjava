package erland.game.tetris;

import java.awt.*;

/**
 * Class that keeps track of the color and state on
 * all squares in a game area
 */
class BlockContainerData implements BlockMatrix {
    /** Matrix that keeps track of the state of all squares */
    protected boolean matrix[][];
    /** Matrix that keeps track of the colour of all squares */
    protected Color colorMatrix[][];

    /**
     * Create a new instance
     * @param sizeX Number of horizontal squares
     * @param sizeY Number of vertical squares
     */
    BlockContainerData(int sizeX, int sizeY)
    {
        matrix = new boolean[sizeX][sizeY];
        colorMatrix = new Color[sizeX][sizeY];
    }

    /**
     * Reset the states on all squares
     */
    public void clear()
    {
        for(int y=0;y<getHeight();y++) {
            clearRow(y);
        }
    }

    /**
     * Reset the states on all squares in a row
     * @param y Vertical row that should be cleared, 0 is the top row
     */
    public void clearRow(int y)
    {
        for(int x=0;x<getWidth();x++) {
            matrix[x][y]=false;
        }
    }

    public Color getColor(int x, int y)
    {
        if(x>=0 && x<matrix.length && y<matrix[0].length && y>=0) {
            return colorMatrix[x][y];
        }else {
            return Color.black;
        }
    }

    public int getWidth()
    {
        return matrix.length;
    }
    public int getHeight()
    {
        return matrix[0].length;
    }

    public boolean isUsed(int x, int y)
    {
        if(x>=0 && x<matrix.length && y<matrix[0].length) {
            if(y>=0) {
                return matrix[x][y];
            }else {
                return false;
            }
        }else {
            return true;
        }
    }

    public void setUsed(int x, int y, Color c)
    {
        if(x>=0 && x<matrix.length && y>=0 && y<matrix[0].length) {
            matrix[x][y]=true;
            colorMatrix[x][y]=c;
        }
    }
    public void setUnused(int x, int y)
    {
        if(x>=0 && x<matrix.length && y>=0 && y<matrix[0].length) {
            matrix[x][y] = false;
        }
    }

    public void fromString(String data) {
        if(data.length()>=matrix.length*matrix[0].length) {
            for(int i=0;i<matrix.length*matrix[0].length;i++) {
                char c=data.charAt(i);
                Color col=null;
                if(c=='0') {
                    col = null;
                }else if(c=='1') {
                    col = Color.red;
                }else if(c=='2') {
                    col = Color.green;
                }else if(c=='3') {
                    col = Color.yellow;
                }else if(c=='4') {
                    col = Color.cyan;
                }else if(c=='5') {
                    col = Color.blue;
                }else if(c=='6') {
                    col = Color.pink;
                }else if(c=='7') {
                    col = Color.magenta;
                }
                if(col==null) {
                    matrix[i/matrix[0].length][i%matrix[0].length]=false;
                    colorMatrix[i/matrix[0].length][i%matrix[0].length]=null;
                }else {
                    matrix[i/matrix[0].length][i%matrix[0].length]=true;
                    colorMatrix[i/matrix[0].length][i%matrix[0].length]=col;
                }
            }
        }
    }
    public String toString() {
        StringBuffer sb = new StringBuffer(matrix.length*matrix[0].length);
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                boolean bUsed = matrix[x][y];
                if(bUsed) {
                    if(colorMatrix[x][y]==Color.red) {
                        sb.append('1');
                    }else if(colorMatrix[x][y]==Color.green) {
                        sb.append('2');
                    }else if(colorMatrix[x][y]==Color.yellow) {
                        sb.append('3');
                    }else if(colorMatrix[x][y]==Color.cyan) {
                        sb.append('4');
                    }else if(colorMatrix[x][y]==Color.blue) {
                        sb.append('5');
                    }else if(colorMatrix[x][y]==Color.pink) {
                        sb.append('6');
                    }else if(colorMatrix[x][y]==Color.magenta) {
                        sb.append('7');
                    }
                }else {
                    sb.append('0');
                }
            }
        }
        return sb.toString();
    }
}
