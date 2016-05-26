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
    private static BufferedImage smallUserImage;
    private static BufferedImage largeUserImage;

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

    public synchronized static BufferedImage getSmallUserImage() {
        if(smallUserImage == null) {
            try {
                smallUserImage = ImageIO.read(Images.class.getResource("/images/images (2).jpg"));
            } catch (Exception e) {
                smallUserImage = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
            }
        }
        return smallUserImage;
    }

    public synchronized static BufferedImage getLargeUserImage() {
        if(largeUserImage == null) {
            try {
                largeUserImage = ImageIO.read(Images.class.getResource("/images/User-icon.png"));
            } catch (Exception e) {
                largeUserImage = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
            }
        }
        return largeUserImage;
    }

    public static BufferedImage getUserImage(boolean small) {
        return small ? getSmallUserImage() : getLargeUserImage();
    }
}
