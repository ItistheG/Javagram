package gui;

import misc.GuiHelper;
import misc.MyScrollbarUI;
import resources.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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

    public String getMessageText() {
        return this.messageTextArea.getText();
    }

    public void setMessageText(String text) {
        this.messageTextArea.setText(text);
    }
}
