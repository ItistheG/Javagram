package gui;

import misc.MaxLengthDocumentFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by HerrSergio on 06.04.2016.
 */
public class CodeForm extends JPanel {
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
        if(codePasswordField.getDocument() instanceof AbstractDocument)
            ((AbstractDocument) codePasswordField.getDocument()).setDocumentFilter(new MaxLengthDocumentFilter(5));

        this.okButton.setContentAreaFilled(false);
        this.okButton.setOpaque(true);

        this.iconPanel.setBorder(BorderFactory.createEmptyBorder());
    }

    public void setPhoneLabelText(String text) {
        phoneLabel.setText(text);
    }

    public String getPhoneLabelText() {
        return phoneLabel.getText();
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

    public void addActionListenerForConfirm(ActionListener actionListener) {
        okButton.addActionListener(actionListener);
        codePasswordField.addActionListener(actionListener);
    }

    public void removeActionListenerForConfirm(ActionListener actionListener) {
        okButton.removeActionListener(actionListener);
        codePasswordField.removeActionListener(actionListener);
    }

    public String getCode() {
        return new String(this.codePasswordField.getPassword());
    }
}
