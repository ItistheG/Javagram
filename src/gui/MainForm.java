package gui;

import misc.GuiHelper;
import misc.MyScrollbarUI;
import resources.Fonts;
import resources.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;


/**
 * Created by HerrSergio on 06.04.2016.
 */
public class MainForm extends JPanel {
    private JPanel rooPanel;
    private JPanel titlePanel;
    private JPanel contactsPanel;
    private JPanel messagesPanel;
    private JPanel bottomPanel;
    private JScrollPane contactsScrollPane;
    private JPanel testPanel;
    private JTextArea messageTextArea;
    private JButton sendMessageButton;
    private JScrollPane messageTextScrollPane;
    private JButton gearButton;

    private String text;

    {
        contactsPanel.add(new JPanel());
        messagesPanel.add(new JPanel());

        GuiHelper.decorateScrollPane(messageTextScrollPane);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rooPanel = this;

        testPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.white);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                g.setColor(Color.cyan);
                g.drawRect(100, 100, 200, 200);
            }
        };

        titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0x82B7E8));
                g.fillRect(0, 0, this.getWidth(), this.getHeight());

                if(text == null)
                    return;

                int inset = 25;
                Font font = Fonts.getNameFont().deriveFont(Font.ITALIC, 30);
                String line = text;

                int x = inset;
                int maxWidth = (gearButton.getX() - x - inset);
                FontMetrics fontMetrics = getFontMetrics(font);

                while(fontMetrics.stringWidth(line) > maxWidth) {
                    if(line.length() > 3)
                        line = line.substring(0, line.length() - 4) + "...";
                    else
                        return;
                }

                LineMetrics lineMetrics = fontMetrics.getLineMetrics(line, g);
                int y = (int)Math.round((this.getHeight() - lineMetrics.getHeight()) / 2.0 + fontMetrics.getAscent());

                x += maxWidth - fontMetrics.stringWidth(line);

                g.setColor(Color.white);
                g.setFont(font);
                g.drawString(line, x, y);
            }
        };

        gearButton = new JButton() {
            @Override
            protected void paintComponent(Graphics graphics) {
                //super.paintComponent(graphics);
                GuiHelper.drawImage(graphics, Images.getGearIcon(), 0, 0, this.getWidth(), this.getHeight());
            }

            @Override
            protected void paintBorder(Graphics graphics) {
                //super.paintBorder(graphics);
            }
        };

        sendMessageButton = new JButton() {
            @Override
            protected void paintComponent(Graphics graphics) {
                //super.paintComponent(graphics);
                GuiHelper.drawImage(graphics, Images.getSendMessageImage(), 0, 0, this.getWidth(), this.getHeight());
            }

            @Override
            protected void paintBorder(Graphics graphics) {
                //super.paintBorder(graphics);
            }
        };
    }

    public Component getContactsPanel() {
        return this.contactsPanel.getComponent(0);
    }

    public void setContactsPanel(Component contactsPanel) {
        this.contactsPanel.removeAll();
        this.contactsPanel.add(contactsPanel);
    }

    public Component getMessagesPanel() {
        return this.messagesPanel.getComponent(0);
    }

    public void setMessagesPanel(Component messagesPanel) {
        this.messagesPanel.removeAll();
        this.messagesPanel.add(messagesPanel);
    }

    public void addSendMessageListener(ActionListener listener) {
        this.sendMessageButton.addActionListener(listener);
    }

    public void removeSendMessageListener(ActionListener listener) {
        this.sendMessageButton.removeActionListener(listener);
    }

    public void removeGearEventListener(ActionListener listener) {
        this.gearButton.removeActionListener(listener);
    }

    public void addGearEventListener(ActionListener listener) {
        this.gearButton.addActionListener(listener);
    }


    public String getMessageText() {
        return this.messageTextArea.getText();
    }

    public void setMessageText(String text) {
        this.messageTextArea.setText(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }


}
