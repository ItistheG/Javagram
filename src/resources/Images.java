package resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by HerrSergio on 25.05.2016.
 */
public class Images {
    private Images() {
    }

    private static BufferedImage background;
    private static BufferedImage logo;

    public synchronized static BufferedImage getBackground() {
        if(background == null) {
            try {
                background = ImageIO.read(Images.class.getResource("/images/Writing_a_letter.jpg"));
            } catch (Exception e) {
                background = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
            }
        }
        return background;
    }

    public synchronized static BufferedImage getLogo() {
        if(logo == null) {
            try {
                logo = ImageIO.read(Images.class.getResource("/images/pre_mail.png"));
            } catch (Exception e) {
                logo = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
            }
        }
        return logo;
    }
}
