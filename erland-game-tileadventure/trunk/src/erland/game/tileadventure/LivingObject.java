package erland.game.tileadventure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

public class LivingObject extends MovableObject {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(LivingObject.class);
    private boolean bJumping;
    private final static int JUMPING_PROGRESS_PER_LEVEL_MAX=15;
    private float jumpingProgress;
    private ActionInterface jumpingAction;
    private final static int JUMPING_HEIGHT=2;
    private int lastJumpingProgress;
    private float jumpingSpeed;
    private double jumpingEnd;
/*
    protected Action isActionPossible(Action action) {
        if(!super.isMoving()) {
            if(!bJumping && !isDropping()) {
                if(action==Action.JUMP && !getActionMap().isFree(this,(int)getPosX(),(int)getPosY(),(int)getPosZ()-1)) {
                    return getActionMap().isActionPossibleOnObject(this,action);
                }else {
                    return super.isActionPossible(action);
                }
            }else {
                return super.isActionPossible(action);
            }
        }
        return Action.NONE;
    }
    protected boolean actionChild(Action action) {
        if(!super.isMoving()) {
            if(!bJumping && !isDropping()) {
                if(action==Action.JUMP && !getActionMap().isFree(this,(int)getPosX(),(int)getPosY(),(int)getPosZ()-1)) {
                    ActionInterface a = getActionMap().startActionOnObject(this,action);
                    LOG.debug("Starting JUMP trying");
                    if(a!=null && a!=Action.NONE) {
                        LOG.debug("Starting JUMP success");
                        bJumping = true;
                        jumpingProgress = 0;
                        lastJumpingProgress =0;
                        jumpingAction = a;
                        jumpingEnd = Math.sqrt(JUMPING_HEIGHT);
                        jumpingSpeed = (float)jumpingEnd/(JUMPING_HEIGHT*(JUMPING_PROGRESS_PER_LEVEL_MAX-1));
                        LOG.debug("jumpingSpeed="+jumpingSpeed);
                        return true;
                    }
                }else {
                    return super.actionChild(action);
                }
            }else {
                return super.actionChild(action);
            }
        }
        return true;
    }

    public boolean update() {
        boolean bUpdated = false;
        if(!bJumping) {
            bUpdated = super.update();
        }else {
            LOG.debug(getMovingProgressZ()+","+lastJumpingProgress);
            jumpingProgress+=jumpingSpeed;
            if(jumpingProgress>=jumpingEnd) {
                getActionMap().endActionOnObject(this,jumpingAction);
                LOG.debug("Jumping finished "+getPosZ());
                bJumping = false;
                bUpdated = true;
            }else if(lastJumpingProgress!=(int)getJumpingProgress()) {
                getActionMap().endActionOnObject(this,jumpingAction);
                lastJumpingProgress=(int)getJumpingProgress();
                ActionInterface a = getActionMap().startActionOnObject(this,jumpingAction);
                if(a==jumpingAction) {
                    LOG.debug("Jumping, next block "+getPosZ()+" allowed");
                }else {
                    LOG.debug("Jumping, next block not allowed");
                    bJumping=false;
                }
                bUpdated = true;
            }
            if(isMoving()) {
                bUpdated = super.update()?true:bUpdated;
            }
        }
        return bUpdated;
    }

    protected float getJumpingProgress() {
        return (float)(2f*jumpingProgress*Math.sqrt(JUMPING_HEIGHT)-jumpingProgress*jumpingProgress);
    }
    protected float getMovingProgressZ() {
        if(!bJumping) {
            return super.getMovingProgressZ();
        }else {
            return getJumpingProgress()-lastJumpingProgress;
        }
    }

    public float getMovingPosZ() {
        if(bJumping) {
            return super.getMovingPosZ()+(int)getJumpingProgress()-lastJumpingProgress;
        }else {
            return super.getMovingPosZ();
        }
    }
    */
    public void draw(Graphics g) {
        super.draw(g);    //To change body of overridden methods use File | Settings | File Templates.
        if(LOG.isDebugEnabled()) {
            if(getContainer().getVisible(getPosX(),getPosY(),getPosZ())) {
                Box3D box = getBoundingBox();
                int x1 = getContainer().getPixelDrawingPositionX(box.getMinX(),box.getMinY(),box.getMinZ());
                int x2 = getContainer().getPixelDrawingPositionX(box.getMinX(),box.getMinY(),box.getMaxZ());
                int x3 = getContainer().getPixelDrawingPositionX(box.getMinX(),box.getMaxY(),box.getMinZ());
                int x4 = getContainer().getPixelDrawingPositionX(box.getMinX(),box.getMaxY(),box.getMaxZ());
                int x5 = getContainer().getPixelDrawingPositionX(box.getMaxX(),box.getMinY(),box.getMinZ());
                int x6 = getContainer().getPixelDrawingPositionX(box.getMaxX(),box.getMinY(),box.getMaxZ());
                int x7 = getContainer().getPixelDrawingPositionX(box.getMaxX(),box.getMaxY(),box.getMinZ());
                int x8 = getContainer().getPixelDrawingPositionX(box.getMaxX(),box.getMaxY(),box.getMaxZ());

                int y1 = getContainer().getPixelDrawingPositionY(box.getMinX(),box.getMinY(),box.getMinZ());
                int y2 = getContainer().getPixelDrawingPositionY(box.getMinX(),box.getMinY(),box.getMaxZ());
                int y3 = getContainer().getPixelDrawingPositionY(box.getMinX(),box.getMaxY(),box.getMinZ());
                int y4 = getContainer().getPixelDrawingPositionY(box.getMinX(),box.getMaxY(),box.getMaxZ());
                int y5 = getContainer().getPixelDrawingPositionY(box.getMaxX(),box.getMinY(),box.getMinZ());
                int y6 = getContainer().getPixelDrawingPositionY(box.getMaxX(),box.getMinY(),box.getMaxZ());
                int y7 = getContainer().getPixelDrawingPositionY(box.getMaxX(),box.getMaxY(),box.getMinZ());
                int y8 = getContainer().getPixelDrawingPositionY(box.getMaxX(),box.getMaxY(),box.getMaxZ());

                g.setColor(Color.WHITE);
                g.drawLine(x1,y1,x2,y2);
                g.drawLine(x3,y3,x4,y4);
                g.drawLine(x1,y1,x3,y3);
                g.drawLine(x2,y2,x4,y4);

                g.setColor(Color.WHITE);
                g.drawLine(x5,y5,x6,y6);
                g.drawLine(x7,y7,x8,y8);
                g.drawLine(x5,y5,x7,y7);
                g.drawLine(x6,y6,x8,y8);

                g.setColor(Color.WHITE);
                g.drawLine(x1,y1,x5,y5);
                g.drawLine(x2,y2,x6,y6);
                g.drawLine(x3,y3,x7,y7);
                g.drawLine(x4,y4,x8,y8);
            }
        }
    }
}
