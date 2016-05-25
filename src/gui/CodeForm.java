package gui;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by HerrSergio on 06.04.2016.
 */
public class CodeForm {
    private JPanel iconPanel;
    private JLabel phoneLabel;
    private JTextPane hintTextPane;
    private JPanel rootPanel;
    private JPanel phonePanel;
    private JPasswordField codePasswordField;
    private JButton okButton;

    private BufferedImage mainImage;
    private BufferedImage iconImage;

    {
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
        hintTextPane.setParagraphAttributes(attribs, false);

        codePasswordField.setBorder(BorderFactory.createEmptyBorder());

        this.okButton.setContentAreaFilled(false);
        this.okButton.setOpaque(true);

        this.iconPanel.setBorder(BorderFactory.createEmptyBorder());
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JLabel getPhoneLabel() {
        return phoneLabel;
    }

    public BufferedImage getMainImage() {
        return mainImage;
    }

    public void setMainImage(BufferedImage mainImage) {
        this.mainImage = mainImage;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if(mainImage == null)
                    return;

                g.drawImage(mainImage, 0, 0, this.getWidth(), this.getHeight(), null);

            }
        };

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
    }

    public void runOnNextEvent(Runnable run) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run.run();
            }
        };

        this.okButton.addActionListener(actionListener);
        this.codePasswordField.addActionListener(actionListener);
    }

    public String getCode() {
        return new String(this.codePasswordField.getPassword());
    }
}
