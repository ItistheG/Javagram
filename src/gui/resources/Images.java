package gui.resources;

import components.GuiHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    private static BufferedImage penLogo;
    private static BufferedImage addContact;
    private static BufferedImage updateContact;
    private static BufferedImage removeContact;
    private static BufferedImage closeOverlay;
    private static BufferedImage logoutIcon;
  //  private static BufferedImage blueButton;
    private static Icon warningIcon;
    private static Icon questionIcon;
    private static Icon errorIcon;
    private static Icon informationIcon;
    private static BufferedImage appIcon;
    private static BufferedImage phoneIcon;
    private static BufferedImage lockIcon;

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

    public static BufferedImage getPenLogo() {
        if(penLogo == null)
            penLogo = loadImage("handposition3png.png");
        return penLogo;
    }

    public static BufferedImage getAddContact() {
        if(addContact == null)
            addContact = loadImage("Add-Male-User.png");
        return addContact;
    }

    public static BufferedImage getUpdateContact() {
        if(updateContact == null)
            updateContact = loadImage("43781db5c40ecc39fd718685594f0956.png");
        return updateContact;
    }

    public static BufferedImage getRemoveContact() {
        if(removeContact == null)
            removeContact = loadImage("Remove-Male-User.png");
        return removeContact;
    }

    public static BufferedImage getCloseOverlay() {
        if(closeOverlay == null)
            closeOverlay = loadImage("Close.png");
        return closeOverlay;
    }

    public static BufferedImage getLogoutIcon() {
        if(logoutIcon == null)
            logoutIcon = loadImage("logout-icon.png");
        return logoutIcon;
    }

    public static Icon getWarningIcon() {
        if(warningIcon == null)
            warningIcon = scaleImageToIcon(loadImage("2000px-Warning_icon.svg.png"));
        return warningIcon;
    }

    public static Icon getErrorIcon() {
        if(errorIcon == null)
            errorIcon = scaleImageToIcon(loadImage("1195445190322000997molumen_red_round_error_warning_icon.svg.hi.png"));
        return errorIcon;
    }

    public static Icon getQuestionIcon() {
        if(questionIcon == null)
            questionIcon = scaleImageToIcon(loadImage("Icon-question.png"));
        return questionIcon;
    }

    public static Icon getInformationIcon() {
        if(informationIcon == null)
            informationIcon = scaleImageToIcon(loadImage("2000px-Circle-information.svg.png"));
        return informationIcon;
    }

    public static BufferedImage getAppIcon() {
        if(appIcon == null)
            appIcon = loadImage("iMessage-icon.png");
        return appIcon;
    }

    public static BufferedImage getPhoneIcon() {
        if(phoneIcon == null)
            phoneIcon = loadImage("phone-icon-hi.png");
        return phoneIcon;
    }

    public static BufferedImage getLockIcon() {
        if(lockIcon == null)
            lockIcon = loadImage("padlock_closed_inv.png");
        return lockIcon;
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
        return GuiHelper.loadImage("images/" + name, Images.class);
    }

    private static Icon scaleImageToIcon(BufferedImage image) {
        return new ImageIcon(GuiHelper.scaleImage(image, 50, 50));
    }
}
