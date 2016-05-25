package undecorated;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by HerrSergio on 24.05.2016.
 */
public class UndecoratedFrame extends JPanel {
    private JPanel rootPanel;
    private JPanel topPanel;
    private JPanel contentPanel;
    private JButton closeButton;
    private JButton minimizeButton;

    private ComponentMover componentMover;
    private ComponentResizerAbstract componentResizerExtended;

    public UndecoratedFrame(Window window, int resizePolicy) {
        componentMover = new ComponentMover(window, topPanel);
        if(resizePolicy < 0)
            return;
        componentResizerExtended = new ComponentResizerAbstract(resizePolicy, window) {

            @Override
            protected int getExtraHeight() {
                return rootPanel.getHeight() - contentPanel.getHeight();
            }

            @Override
            protected int getExtraWidth() {
                return rootPanel.getWidth() - contentPanel.getWidth();
            }
        };
    }

    public UndecoratedFrame(JFrame window, int resizePolicy){
        this((Window)window, resizePolicy);
        window.setUndecorated(true);
        window.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        window.setContentPane(this);
    }

    public UndecoratedFrame(JDialog window, int resizePolicy){
        this((Window)window, resizePolicy);
        window.setUndecorated(true);
        window.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        window.setContentPane(this);
    }


    public void setContentPanel(Component component) {
        contentPanel.removeAll();
        contentPanel.add(component);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

        closeButton = new JButton() {
            @Override
            protected void paintComponent(Graphics graphics) {
                //super.paintComponent(graphics);
                int width = this.getWidth();
                int height = this.getHeight();
                int x = width / 6;
                int y = height / 6;

                graphics.drawLine(x, y, width - x, height - y);
                graphics.drawLine(x, height - y, width - x, y);

            }
        };

        minimizeButton = new JButton() {
            @Override
            protected void paintComponent(Graphics graphics) {
                //super.paintComponent(graphics);
                int width = this.getWidth();
                int height = this.getHeight();
                int x = width / 6;
                int y = (height * 2) / 3;

                graphics.drawLine(x, y, width - x, y);

            }
        };
    }

    public void addActionListenerForClose(ActionListener listener) {
        closeButton.addActionListener(listener);
    }

    public void addActionListenerForMinimize(ActionListener listener) {
        minimizeButton.addActionListener(listener);
    }

    public void removeActionListenerForClose(ActionListener listener) {
        closeButton.removeActionListener(listener);
    }

    public void removeActionListenerForMinimize(ActionListener listener) {
        minimizeButton.removeActionListener(listener);
    }
}