package erland.game.crackout;

import java.awt.*;

interface ImageHandlerInterface
{
	static final int FEATURE=0;
	static final int FEATURE_LOCKBAT=1;
	static final int FEATURE_NEWBALL=2;
	static final int FEATURE_INCBALLSPEED=3;
	static final int FEATURE_DECBALLSPEED=4;
	static final int FEATURE_INCBATSPEED=5;
	static final int FEATURE_DECBATSPEED=6;
	static final int FEATURE_DOUBLEBAT=7;
	static final int FEATURE_EXTRALIFE=8;
	static final int FEATURE_SAFETYWALL=9;
	static final int BUTTON_ARROWDOWN=10;
	static final int BUTTON_ARROWUP=11;
	static final int MONSTER_BOUNCEBLOCK=12;
	static final int MONSTER_BOUNCEONCEBLOCK=13;
	static final int FEATURE_MISSILE=14;
	static final int FEATURE_LARGEBAT=15;
	static final int FEATURE_SMALLBAT=16;
	static final int MISSILE=17;
	static final int FEATURE_BOMB=18;
	static final int BALL=19;
	static final int NO_OF_IMAGES=20;

	Image getImage(int image);
}
