package gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;

/**
 * Created by HerrSergio on 05.04.2016.
 */
public class PhoneForm extends JPanel {
    private JPanel rootPanel;
    private JPanel iconPanel;
    private JTextPane hintTextPane;
    private JFormattedTextField phoneTextField;
    private JPanel phonePanel;
    private JButton okButton;

    private BufferedImage mainImage;
    private BufferedImage iconImage;

    {
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_CENTER);
        hintTextPane.setParagraphAttributes(attribs, false);

        phoneTextField.setBorder(BorderFactory.createEmptyBorder());

        try {
            MaskFormatter maskFormatter = new MaskFormatter("+7 (###) ###-##-##");
            maskFormatter.setPlaceholder(null);
            maskFormatter.setPlaceholderCharacter('.');
            phoneTextField.setFormatterFactory(new DefaultFormatterFactory(maskFormatter));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.okButton.setContentAreaFilled(false);
        this.okButton.setOpaque(true);

        this.iconPanel.setBorder(BorderFactory.createEmptyBorder());
    }

    public String getPhoneNumber() {
        try {
            phoneTextField.commitEdit();
            return phoneTextField.getValue().toString();
        } catch (ParseException|NullPointerException e) {
            return null;
        }
    }

    public void addActionListenerForConfirm(ActionListener actionListener) {
        okButton.addActionListener(actionListener);
        phoneTextField.addActionListener(actionListener);
    }

    public void removeActionListenerForConfirm(ActionListener actionListener) {
        okButton.removeActionListener(actionListener);
        phoneTextField.removeActionListener(actionListener);
    }

    public BufferedImage getMainImage() {
        return mainImage;
    }

    public void setMainImage(BufferedImage mainImage) {
        this.mainImage = mainImage;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(mainImage == null)
            return;

        g.drawImage(mainImage, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

        iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if(iconImage == null)
                    return;

                g.drawImage(iconImage, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
    }



    public BufferedImage getIconImage() {
        return iconImage;
    }

    public void setIconImage(BufferedImage iconImage) {
        this.iconImage = iconImage;
        repaint();
    }
}
