package erland.game.isoadventure;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
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

public class IsoObjectLiving extends IsoBitmapObjectMovable {
    private boolean bJumping;
    private final static int JUMPING_PROGRESS_PER_LEVEL_MAX=15;
    private float jumpingProgress;
    private Action jumpingAction;
    private final static int JUMPING_HEIGHT=2;
    private int lastJumpingProgress;
    private float jumpingSpeed;
    private double jumpingEnd;

    public boolean action(Action action) {
        if(!super.isMoving()) {
            if(!bJumping && !isDropping()) {
                if(action==Action.JUMP && !getActionMap().isFree(this,getPosX(),getPosY(),getPosZ()-1)) {
                    Action a = getActionMap().startActionOnObject(this,action);
                    System.out.println("Starting JUMP trying");
                    if(a!=null && a!=Action.NONE) {
                        System.out.println("Starting JUMP success");
                        bJumping = true;
                        jumpingProgress = 0;
                        lastJumpingProgress =0;
                        jumpingAction = a;
                        jumpingEnd = Math.sqrt(JUMPING_HEIGHT);
                        jumpingSpeed = (float)jumpingEnd/(JUMPING_HEIGHT*(JUMPING_PROGRESS_PER_LEVEL_MAX-1));
                        System.out.println("jumpingSpeed="+jumpingSpeed);
                        return true;
                    }
                }else {
                    return super.action(action);
                }
            }else {
                return super.action(action);
            }
        }
        return false;
    }

    public void update() {
        if(!bJumping) {
            super.update();
        }else {
            System.out.println(getMovingProgressZ()+","+lastJumpingProgress);
            jumpingProgress+=jumpingSpeed;
            if(jumpingProgress>=jumpingEnd) {
                getActionMap().endActionOnObject(this,jumpingAction);
                System.out.println("Jumping finished "+getPosZ());
                bJumping = false;
            }else if(lastJumpingProgress!=(int)getJumpingProgress()) {
                getActionMap().endActionOnObject(this,jumpingAction);
                lastJumpingProgress=(int)getJumpingProgress();
                Action a = getActionMap().startActionOnObject(this,jumpingAction);
                if(a==jumpingAction) {
                    System.out.println("Jumping, next block "+getPosZ()+" allowed");
                }else {
                    System.out.println("Jumping, next block not allowed");
                    bJumping=false;
                }
            }
            if(isMoving()) {
                super.update();
            }
        }
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

    public int getMovingPosZ() {
        if(bJumping) {
            return super.getMovingPosZ()+(int)getJumpingProgress()-lastJumpingProgress;
        }else {
            return super.getMovingPosZ();
        }
    }
}
