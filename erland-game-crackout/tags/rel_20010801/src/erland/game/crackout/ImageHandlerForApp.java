package erland.game.crackout;

import java.awt.*;
import java.io.*;

class ImageHandlerForApp
	implements ImageHandlerInterface
{
	Image images[];
	
	ImageHandlerForApp(Component comp)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();

		this.images = new Image[NO_OF_IMAGES];
		String file="images/crackout/feature.gif";
		this.images[FEATURE] = tk.getImage(file);
		file="images/crackout/feature_lockbat.gif";
		this.images[FEATURE_LOCKBAT] = tk.getImage(file);
		file="images/crackout/feature_newball.gif";
		this.images[FEATURE_NEWBALL] = tk.getImage(file);
		file="images/crackout/feature_incballspeed.gif";
		this.images[FEATURE_INCBALLSPEED] = tk.getImage(file);
		file="images/crackout/feature_decballspeed.gif";
		this.images[FEATURE_DECBALLSPEED] = tk.getImage(file);
		file="images/crackout/feature_incbatspeed.gif";
		this.images[FEATURE_INCBATSPEED] = tk.getImage(file);
		file="images/crackout/feature_decbatspeed.gif";
		this.images[FEATURE_DECBATSPEED] = tk.getImage(file);
		file="images/crackout/feature_doublebat.gif";
		this.images[FEATURE_DOUBLEBAT] = tk.getImage(file);
		file="images/crackout/feature_extralife.gif";
		this.images[FEATURE_EXTRALIFE] = tk.getImage(file);
		file="images/crackout/feature_safetywall.gif";
		this.images[FEATURE_SAFETYWALL] = tk.getImage(file);
		file="images/crackout/button_arrowdown.gif";
		this.images[BUTTON_ARROWDOWN] = tk.getImage(file);
		file="images/crackout/button_arrowup.gif";
		this.images[BUTTON_ARROWUP] = tk.getImage(file);
		file="images/crackout/monster_bounceblock.gif";
		this.images[MONSTER_BOUNCEBLOCK] = tk.getImage(file);
		file="images/crackout/monster_bounceonceblock.gif";
		this.images[MONSTER_BOUNCEONCEBLOCK] = tk.getImage(file);
		file="images/crackout/feature_missile.gif";
		this.images[FEATURE_MISSILE] = tk.getImage(file);
		file="images/crackout/feature_largebat.gif";
		this.images[FEATURE_LARGEBAT] = tk.getImage(file);
		file="images/crackout/feature_smallbat.gif";
		this.images[FEATURE_SMALLBAT] = tk.getImage(file);
		file="images/crackout/missile.gif";
		this.images[MISSILE] = tk.getImage(file);
		file="images/crackout/feature_bomb.gif";
		this.images[FEATURE_BOMB] = tk.getImage(file);
		file="images/crackout/ball.gif";
		this.images[BALL] = tk.getImage(file);
		MediaTracker mt = new MediaTracker(comp);
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