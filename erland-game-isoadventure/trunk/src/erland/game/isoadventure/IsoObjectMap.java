package erland.game.isoadventure;

public class IsoObjectMap implements IsoObjectMapInterface {
    private IsoObjectInterface[][][] map;
    public IsoObjectMap(int sizeX, int sizeY, int sizeZ) {
        map = new IsoObjectInterface[sizeZ][sizeX][sizeY];
    }
    public void addObject(IsoObjectInterface obj, int x, int y, int z) {
        if(isInsideMap(x,y,z)) {
            map[z][x][y] = obj;
        }
    }

    public void removeObject(IsoObjectInterface obj, int x, int y, int z) {
        if(isInsideMap(x,y,z)) {
            if(map[z][x][y]==obj) {
                map[z][x][y] = null;
            }
        }
    }

    public void removeObjectFromMap(IsoObjectInterface obj) {
        for(int z=0;z<map.length;z++) {
            for(int x=0;x<map[0].length;x++) {
                for(int y=0;y<map[0][0].length;y++) {
                    if(map[z][x][y]==obj) {
                        map[z][x][y]=null;
                    }
                }
            }
        }
    }

    private boolean isInsideMap(int x, int y, int z) {
        if(x<map[0].length && y<map[0][0].length && z<map.length && x>=0 && y>=0 && z>=0) {
            return true;
        }else {
            return false;
        }
    }
    public IsoObjectInterface getObject(int x, int y, int z) {
        if(isInsideMap(x,y,z)) {
            return map[z][x][y];
        }else {
            return null;
        }
    }

    public int getSizeX() {
        return map[0].length;
    }

    public int getSizeY() {
        return map[0][0].length;
    }

    public int getSizeZ() {
        return map.length;
    }
}
