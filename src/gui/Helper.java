package gui;

import components.GuiHelper;
import gui.overlays.ContactInfo;
import gui.resources.Images;
import mvp.model.abs.TelegramModel;
import org.javagram.dao.KnownPerson;
import org.javagram.dao.Person;
import org.javagram.dao.proxy.TelegramProxy;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by HerrSergio on 23.08.2016.
 */
public class Helper {

    private Helper() {
    }

    public static void adjustTextPane(JTextPane textPane) {
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
        textPane.setParagraphAttributes(attribs, false);
        //Для Nimbus
        clearBoth(textPane);
    }

    public static void clearBoth(JComponent textPane) {
        clearBackground(textPane);
        clearBorder(textPane);
    }

    public static void clearBackground(JComponent component) {
        component.setOpaque(false);
        component.setBackground(new Color(0, 0, 0, 0));//Для Nimbus

    }

    public static void clearBorder(JComponent component) {
        component.setBorder(BorderFactory.createEmptyBorder());
    }


    public static BufferedImage makeCircle(BufferedImage image) {
        return image != null ? GuiHelper.makeCircle(image) : null ;
    }

    public static BufferedImage getPhoto(BufferedImage image, boolean small) {
        if(image == null)
            image = Images.getUserImage(small);
        return image;
    }

    public static BufferedImage getPhoto(Map<Person, BufferedImage> photos, Person person, boolean small) {
        return getPhoto(photos.get(person), small);
    }

    public static BufferedImage getPhoto(Map<Person, BufferedImage> photos, Person person, boolean small, boolean circle) {
        BufferedImage photo = getPhoto(photos, person, small);
        if(circle)
            photo = GuiHelper.makeCircle(photo);
        return photo;
    }

}
