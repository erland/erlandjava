package erland.game.crackout;

import java.applet.*;
import java.awt.*;

class ImageHandler
	implements ImageHandlerInterface
{
	Image images[];
	
	ImageHandler(Applet applet)
	{
		this.images = new Image[NO_OF_IMAGES];
		String file="images/crackout/feature.gif";
		this.images[FEATURE] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/feature_lockbat.gif";
		this.images[FEATURE_LOCKBAT] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/feature_newball.gif";
		this.images[FEATURE_NEWBALL] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/feature_incballspeed.gif";
		this.images[FEATURE_INCBALLSPEED] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/feature_decballspeed.gif";
		this.images[FEATURE_DECBALLSPEED] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/feature_incbatspeed.gif";
		this.images[FEATURE_INCBATSPEED] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/feature_decbatspeed.gif";
		this.images[FEATURE_DECBATSPEED] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/feature_doublebat.gif";
		this.images[FEATURE_DOUBLEBAT] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/feature_extralife.gif";
		this.images[FEATURE_EXTRALIFE] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/feature_safetywall.gif";
		this.images[FEATURE_SAFETYWALL] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/button_arrowdown.gif";
		this.images[BUTTON_ARROWDOWN] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/button_arrowup.gif";
		this.images[BUTTON_ARROWUP] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/monster_bounceblock.gif";
		this.images[MONSTER_BOUNCEBLOCK] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/monster_bounceonceblock.gif";
		this.images[MONSTER_BOUNCEONCEBLOCK] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/feature_missile.gif";
		this.images[FEATURE_MISSILE] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/feature_largebat.gif";
		this.images[FEATURE_LARGEBAT] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/feature_smallbat.gif";
		this.images[FEATURE_SMALLBAT] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/missile.gif";
		this.images[MISSILE] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/feature_bomb.gif";
		this.images[FEATURE_BOMB] = applet.getImage(applet.getDocumentBase(),file);
		file="images/crackout/ball.gif";
		this.images[BALL] = applet.getImage(applet.getDocumentBase(),file);
		MediaTracker mt = new MediaTracker(applet);
		for(int i=0;i<NO_OF_IMAGES;i++) {
			mt.addImage(images[i],i+1);
		}
		try {
			mt.waitForAll();
		}catch(InterruptedException e) {
			// Do nothing
		}
	}
	
	public Image getImage(int image)
	{
		return this.images[image];	
	}	
}