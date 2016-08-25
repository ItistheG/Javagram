package gui;

import components.GuiHelper;
import components.MyScrollbarUI;
import org.javagram.dao.Person;
import org.javagram.dao.proxy.TelegramProxy;
import resources.Images;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.image.BufferedImage;

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
    }

    public static BufferedImage getPhoto(TelegramProxy telegramProxy, Person person, boolean small) {
        BufferedImage image;

        try {
            image = telegramProxy.getPhoto(person, small);
        } catch (Exception e) {
            e.printStackTrace();
            image = null;
        }

        if(image == null)
            image = Images.getUserImage(small);
        return image;
    }

    public static BufferedImage getPhoto(TelegramProxy telegramProxy, Person person, boolean small, boolean circle) {
        BufferedImage photo = getPhoto(telegramProxy, person, small);
        if(circle)
            photo = GuiHelper.makeCircle(photo);
        return photo;
    }
}
