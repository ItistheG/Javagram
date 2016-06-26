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
    private static BufferedImage sendMessageImage;
    private static BufferedImage smallUserImage;
    private static BufferedImage largeUserImage;
    private static BufferedImage gearIcon;
    private static BufferedImage plusImage;
    private static BufferedImage magnifyingGlassIcon;
    private static BufferedImage pencilIcon;
    private static BufferedImage penIcon;

    public synchronized static BufferedImage getBackground() {
        if (background == null)
            background = loadImage("Writing_a_letter.jpg");
        return background;
    }

    public synchronized static BufferedImage getLogo() {
        if (logo == null)
            logo = loadImage("pre_mail.png");
        return logo;
    }

    public synchronized static BufferedImage getSendMessageImage() {
        if (sendMessageImage == null)
            sendMessageImage = loadImage("letter2.png");
        return sendMessageImage;
    }

    public static BufferedImage getGearIcon() {
        if(gearIcon == null)
            gearIcon = loadImage("ModelcheckingPlugin.ICON.png");
        return gearIcon;
    }

    public static BufferedImage getPlus() {
        if(plusImage == null)
            plusImage = loadImage("LTKjko5Ta.png");
        return plusImage;
    }

    public static BufferedImage getMagnifyingGlassIcon() {
        if(magnifyingGlassIcon == null)
            magnifyingGlassIcon = loadImage("Magnifying_glass_icon.svg.png");
        return magnifyingGlassIcon;
    }

    public static BufferedImage getPencilIcon() {
        if(pencilIcon == null)
            pencilIcon = loadImage("blue-pencil.jpg");
        return pencilIcon;
    }

    public static BufferedImage getPenIcon() {
        if(penIcon == null)
            penIcon = loadImage("writing-146913_960_720.png");
        return penIcon;
    }

    public synchronized static BufferedImage getSmallUserImage() {
        if (smallUserImage == null)
            smallUserImage = loadImage("images (2).jpg");
        return smallUserImage;
    }

    public synchronized static BufferedImage getLargeUserImage() {
        if (largeUserImage == null)
            largeUserImage = loadImage("User-icon.png");
        return largeUserImage;
    }

    public static BufferedImage getUserImage(boolean small) {
        return small ? getSmallUserImage() : getLargeUserImage();
    }

    private static BufferedImage loadImage(String name) {
        try {
            return ImageIO.read(Images.class.getResource("images/" + name));
        } catch (Exception e) {
            e.printStackTrace();
            return new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        }
    }
}
