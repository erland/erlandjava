package erland.game.isoadventure;

public class IsoBitmapObjectMovable extends IsoBitmapObject {
    private boolean bMoving;
    private Action movingAction;
    private int movingProgress = 0;
    private final int MOVING_PROGRESS_MAX = 20;
    private int movingSpeed=0;
    private boolean bDropping;
    private float droppingProgress = 0;
    private int droppingHeight = 0;
    private float droppingSpeed = 0;
    private final int DROPPING_PROGRESS_MAX = 20;

    public boolean action(Action action) {
        if(!bMoving && (action.isMove() || action.isPush())) {
            Action a = getActionMap().startActionOnObject(this,action);
            if(a.isMove()||a.isPush()) {
                bMoving = true;
                movingAction = a;
                movingProgress = 0;
                if(a.isMove()) {
                    movingSpeed = 2;
                }else {
                    movingSpeed = 1;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isMovable(Direction direction) {
        if(!bMoving) {
            int newX=getPosX();
            int newY=getPosY();
            int newZ=getPosZ();
            if(direction==Direction.WEST) {
                newX--;
            }else if(direction==Direction.EAST) {
                newX++;
            }else if(direction==Direction.NORTH) {
                newY--;
            }else if(direction==Direction.SOUTH) {
                newY++;
            }else if(direction==Direction.DOWN) {
                newZ--;
            }else if(direction==Direction.UP) {
                newZ++;
            }
            if(getActionMap().isFree(this,newX,newY,newZ)) {
                return true;
            }
        }else {
            if(movingAction.getDirection()==direction) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        if(bMoving) {
            movingProgress+=movingSpeed;
            if(movingProgress>=MOVING_PROGRESS_MAX) {
                getActionMap().endActionOnObject(this,movingAction);
                bMoving = false;
            }
        }
        if(bDropping) {
            droppingProgress+=droppingSpeed;
            System.out.println(getMovingProgressZ()+","+droppingProgress+","+droppingHeight);
            if(getMovingProgressZ()<=-1) {
                getActionMap().endActionOnObject(this,Action.DROP);
                droppingHeight++;
                bDropping=false;
            }
        }else {
            droppingHeight=0;
            droppingProgress=0;
        }
        if(!bDropping) {
            Action a = getActionMap().startActionOnObject(this,Action.DROP);
            if(a==Action.DROP) {
                System.out.println("dropping from "+getPosZ());
                bDropping = true;
                droppingSpeed = 1f/DROPPING_PROGRESS_MAX;
            }
        }
    }
    protected float getMovingProgressX() {
        if(bMoving) {
            if(movingAction.getDirection()==Direction.WEST) {
                return -(float)movingProgress/MOVING_PROGRESS_MAX;
            }else if(movingAction.getDirection()==Direction.EAST) {
                return (float)movingProgress/MOVING_PROGRESS_MAX;
            }
        }
        return 0;
    }
    protected float getMovingProgressY() {
        if(bMoving) {
            if(movingAction.getDirection()==Direction.NORTH) {
                return -(float)movingProgress/MOVING_PROGRESS_MAX;
            }else if(movingAction.getDirection()==Direction.SOUTH) {
                return (float)movingProgress/MOVING_PROGRESS_MAX;
            }
        }
        return 0;
    }
    protected float getMovingProgressZ() {
        if(bDropping) {
            return -(float)((droppingProgress)*(droppingProgress)-(droppingHeight));
        }
        return 0;
    }
    public int getDrawingPosX(float dx, float dy, float dz) {
        return super.getDrawingPosX(dx+getMovingProgressX(),dy+getMovingProgressY(),dz+getMovingProgressZ());
    }

    public int getDrawingPosY(float dx, float dy, float dz) {
        return super.getDrawingPosY(dx+getMovingProgressX(),dy+getMovingProgressY(),dz+getMovingProgressZ());
    }
    public int getPixelPosX() {
        return getContainer().getPositionX(getPosX(),getPosY(),getPosZ(),getMovingProgressX(),getMovingProgressY(),getMovingProgressZ());
    }
    public int getPixelPosY() {
        return getContainer().getPositionY(getPosX(),getPosY(),getPosZ(),getMovingProgressX(),getMovingProgressY(),getMovingProgressZ());
    }

    public boolean isMoving() {
        return bMoving;
    }

    protected boolean isDropping() {
        return bDropping;
    }

    public int getMovingPosX() {
        return (int)(getPosX()+getMovingProgressX()/Math.abs(getMovingProgressX()));
    }

    public int getMovingPosY() {
        return (int)(getPosY()+getMovingProgressY()/Math.abs(getMovingProgressY()));
    }

    public int getMovingPosZ() {
        return (int)(getPosZ()+getMovingProgressZ()/Math.abs(getMovingProgressZ()));
    }
}
