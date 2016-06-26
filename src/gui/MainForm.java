package gui;

import misc.GuiHelper;
import misc.HintTextField;
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
    private JPanel rootPanel;
    private JPanel titlePanel;
    private JPanel contactsPanel;
    private JPanel messagesPanel;
    private JPanel testPanel;
    private JTextArea messageTextArea;
    private JButton sendMessageButton;
    private JScrollPane messageTextScrollPane;
    private JButton gearButton;
    private JTextField searchTextField;
    private JPanel searchIconPanel;
    private JPanel buddyPanel;
    private JButton buddyEditButton;

    private String meText;
    private BufferedImage mePhoto;

    private String buddyText;
    private BufferedImage buddyPhoto;


    public MainForm() {
        contactsPanel.add(new JPanel());
        messagesPanel.add(new JPanel());

        GuiHelper.decorateScrollPane(messageTextScrollPane);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

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

                int leftMostPoint = gearButton.getX();

                if (meText != null) {

                    int inset = 25;
                    Font font = Fonts.getNameFont().deriveFont(Font.ITALIC, 30);
                    Color color = Color.white;
                    String text = meText;

                    leftMostPoint = GuiHelper.drawText(g, text, color, font, 0, 0, leftMostPoint, this.getHeight(), inset, true);
                }

                if (mePhoto != null) {
                    int inset = 2;
                    BufferedImage image = mePhoto;

                    leftMostPoint = GuiHelper.drawImage(g, image, 0, 0, leftMostPoint, this.getHeight(), inset, true);
                }

                GuiHelper.drawImage(g, Images.getPenIcon(), 12, 0, leftMostPoint, this.getHeight(), 3, false);
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

        buddyPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                int leftMostPoint = buddyEditButton.getX();

                if (buddyText != null) {

                    int inset = 10;
                    Font font = Fonts.getNameFont().deriveFont(Font.ITALIC, 18);
                    Color color = Color.cyan;
                    String text = buddyText;

                    leftMostPoint = GuiHelper.drawText(graphics, text, color, font, 0, 0, leftMostPoint, this.getHeight(), inset, true);
                }

                if (buddyPhoto != null) {
                    int inset = 2;
                    BufferedImage image = buddyPhoto;

                    GuiHelper.drawImage(graphics, image, 0, 0, leftMostPoint, this.getHeight(), inset, true);
                }

            }
        };

        buddyEditButton = new JButton() {
            @Override
            protected void paintComponent(Graphics graphics) {
                //super.paintComponent(graphics);
                GuiHelper.drawImage(graphics, Images.getPencilIcon(), 0, 0, this.getWidth(), this.getHeight());
            }

            @Override
            protected void paintBorder(Graphics graphics) {
                //super.paintBorder(graphics);
            }
        };

        searchTextField = new HintTextField("", "Поиск...", false) {
            @Override
            protected void paintBorder(Graphics graphics) {
                //super.paintBorder(graphics);
            }
        };

        searchIconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                int inset = 2;
                GuiHelper.drawImage(graphics, Images.getMagnifyingGlassIcon(), inset, inset, this.getWidth() - inset * 2, this.getHeight() - inset * 2);
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

    public void removeBuddyEditEventListener(ActionListener listener) {
        this.buddyEditButton.removeActionListener(listener);
    }

    public void addBuddyEditEventListener(ActionListener listener) {
        this.buddyEditButton.addActionListener(listener);
    }

    public void removeSearchEventListener(ActionListener listener) {
        this.searchTextField.removeActionListener(listener);
    }

    public void addSearchEventListener(ActionListener listener) {
        this.searchTextField.addActionListener(listener);
    }

    public String getSearchText() {
        return this.searchTextField.getText();
    }

    public String getMessageText() {
        return this.messageTextArea.getText();
    }

    public void setMessageText(String text) {
        this.messageTextArea.setText(text);
    }

    public String getMeText() {
        return meText;
    }

    public void setMeText(String meText) {
        this.meText = meText;
        repaint();
    }

    public BufferedImage getMePhoto() {
        return mePhoto;
    }

    public void setMePhoto(BufferedImage mePhoto) {
        this.mePhoto = mePhoto;
        repaint();
    }

    public String getBuddyText() {
        return buddyText;
    }

    public void setBuddyText(String buddyText) {
        this.buddyText = buddyText;
        repaint();
    }

    public BufferedImage getBuddyPhoto() {
        return buddyPhoto;
    }

    public void setBuddyPhoto(BufferedImage buddyPhoto) {
        this.buddyPhoto = buddyPhoto;
        repaint();
    }


}
