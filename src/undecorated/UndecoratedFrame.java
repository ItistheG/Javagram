package undecorated;

import com.sun.deploy.security.MozillaJSSDSASignature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

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
    private String title;

    public static final int CLOSE_BUTTON = 1, MINIMIZE_BUTTON = 4;
    public static final int MINIMIZE_CLOSE_BUTTON = CLOSE_BUTTON | MINIMIZE_BUTTON;
    public static final int NO_BUTTON = 0;

    public UndecoratedFrame(Window window, int resizePolicy, int buttons) {

        setPreferredSize(null);
        setMinimumSize(null);
        setMaximumSize(null);

        if((buttons & CLOSE_BUTTON) == 0)
            topPanel.remove(closeButton);
        if((buttons & MINIMIZE_BUTTON) == 0)
            topPanel.remove(minimizeButton);

        title = "";

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

    public UndecoratedFrame(JWindow window, int resizePolicy, int buttons){
        this((Window)window, resizePolicy, buttons);
        window.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setContentPanel(window.getContentPane());
        window.setContentPane(this);
    }

    public UndecoratedFrame(JFrame window, int resizePolicy, int buttons){
        this((Window)window, resizePolicy, buttons);
        window.setUndecorated(true);
        window.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setContentPanel(window.getContentPane());
        window.setContentPane(this);
        title = window.getTitle();
    }

    public UndecoratedFrame(JFrame window, int resizePolicy) {
        this(window, resizePolicy, MINIMIZE_CLOSE_BUTTON);
    }

    public UndecoratedFrame(JDialog window, int resizePolicy, int buttons){
        this((Window)window, resizePolicy, buttons);
        window.setUndecorated(true);
        window.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setContentPanel(window.getContentPane());
        window.setContentPane(this);
        title = window.getTitle();
    }

    public UndecoratedFrame(JDialog window, int resizePolicy) {
        this(window, resizePolicy, NO_BUTTON);
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

        topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);

                Font font = getFont();
                graphics.setFont(font);

                FontMetrics fontMetrics = graphics.getFontMetrics();
                int height = fontMetrics.getAscent();// + fontMetrics.getDescent();
                int pos = (this.getHeight() + height) / 2;

                int start = 4;
                int end = this.getWidth();
                for(Component component : getComponents()) {
                    if(component.getX() < end)
                        end = component.getX();
                }
                end -= 4;
                String text = title;

                while(fontMetrics.stringWidth(text) > end - start) {
                    int len = text.length() - 4;
                    if(len < 0)
                        return;
                    else
                        text = text.substring(0, len) + "...";
                }

                graphics.setColor(Color.white);
                graphics.drawString(text, start, pos);
            }
        };
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        repaint();
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
