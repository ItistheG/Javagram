package gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by HerrSergio on 05.04.2016.
 */
public class PhoneForm {
    private JPanel rootPanel;
    private JPanel iconPanel;
    private JTextPane hintTextPane;
    private JTextField phoneTextField;
    private JPanel phonePanel;
    private JButton okButton;

    private BufferedImage mainImage;
    private BufferedImage iconImage;

    private final int MAX_PHONE_LENGTH = 15;

    {
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs , StyleConstants.ALIGN_CENTER);
        hintTextPane.setParagraphAttributes(attribs, false);

        phoneTextField.setBorder(BorderFactory.createEmptyBorder());
        phoneTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                needValidation();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                needValidation();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                needValidation();
            }


            private void needValidation() {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String oldText = phoneTextField.getText();
                        String text = oldText;
                        int caretPosition = phoneTextField.getCaretPosition();

                        if(text.length() > MAX_PHONE_LENGTH)
                            text = text.substring(0, MAX_PHONE_LENGTH);
                        text = text.replaceAll("[^ \\d+()-]+", "");
                        if(!oldText.equals(text)) {
                            phoneTextField.setText(text);
                            if(caretPosition <= text.length())
                                phoneTextField.setCaretPosition(caretPosition);
                            else
                                phoneTextField.setCaretPosition(text.length());
                        }
                    }
                });
            }


        });
      //  phoneTextField.setCaretPosition(phoneTextField.getText().length());

        this.okButton.setContentAreaFilled(false);
        this.okButton.setOpaque(true);

        this.iconPanel.setBorder(BorderFactory.createEmptyBorder());
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public String getPhoneNumber() {
        return phoneTextField.getText();//.replaceAll("[\\D]+", "");
    }

    public void runOnNextEvent(Runnable run) {
        if(run == null)
            return;
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run.run();
            }
        };
        okButton.addActionListener(actionListener);
        phoneTextField.addActionListener(actionListener);
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
}
