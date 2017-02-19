package components;

import java.awt.image.BufferedImage;

/**
 * Created by HerrSergio on 19.02.2017.
 */
class Resources {

    private static BufferedImage blueButton;

    private Resources() {
    }

    public static BufferedImage getBlueButton() {
        if(blueButton == null)
            blueButton = GuiHelper.loadImage("expences-button-png-hi.png", Resources.class);
        return blueButton;
    }
}
