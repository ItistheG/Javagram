package gui;

import javax.swing.*;
import java.awt.*;


/**
 * Created by HerrSergio on 06.04.2016.
 */
public class MainForm extends JPanel {
    private JPanel rooPanel;
    private JPanel titlePanel;
    private JPanel contactsPanel;
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JScrollPane contactsScrollPane;
    private JPanel testPanel;


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
    }

    public Component getContactsPanel() {
        return this.contactsPanel.getComponent(0);
    }

    public void setContactsPanel(Component contactsPanel) {
        this.contactsPanel.removeAll();
        this.contactsPanel.add(contactsPanel);
    }
}
