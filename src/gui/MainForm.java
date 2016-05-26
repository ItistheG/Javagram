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

    {
        /*Dimension dim = new Dimension(1000, 1000);
        testPanel.setPreferredSize(dim);
        testPanel.setMinimumSize(dim);
        testPanel.setMaximumSize(dim);

        int width = 3;

        JScrollBar verticalScrollBar =  contactsScrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new MyScrollbarUI());
        verticalScrollBar.setPreferredSize(new Dimension(width, Integer.MAX_VALUE));

        JScrollBar horizontalScrollBar =  contactsScrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setUI(new MyScrollbarUI());
        horizontalScrollBar.setPreferredSize(new Dimension(Integer.MAX_VALUE, width));

        contactsScrollPane.setBorder(BorderFactory.createEmptyBorder());*/
       // contactsScrollPane.revalidate();

        /*for (String corner : new String[] {ScrollPaneConstants.LOWER_RIGHT_CORNER, ScrollPaneConstants.LOWER_LEFT_CORNER,
                ScrollPaneConstants.UPPER_LEFT_CORNER, ScrollPaneConstants.UPPER_RIGHT_CORNER}) {
            contactsScrollPane.setCorner(corner, new MyScrollBarCorner(Color.white));
        }*/
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
    }

    public Component getContactsPanel() {
        return this.contactsPanel.getComponent(0);
    }

    public void setContactsPanel(Component contactsPanel) {
        this.contactsPanel.removeAll();
        this.contactsPanel.add(contactsPanel);
    }
}
