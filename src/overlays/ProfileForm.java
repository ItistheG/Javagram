package overlays;

import misc.GuiHelper;
import org.javagram.dao.Me;
import org.javagram.dao.proxy.TelegramProxy;
import resources.Fonts;
import resources.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by HerrSergio on 15.06.2016.
 */
public class ProfileForm extends JPanel {
    private JButton closeButton;
    private JButton logoutButton;
    private JPanel photoPanel;
    private JPanel rootPanel;
    private JLabel nameLabel;

    private TelegramProxy telegramProxy;

    {
        nameLabel.setFont(Fonts.getNameFont().deriveFont(0, 45));
        nameLabel.setForeground(Color.white);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

        photoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                //super.paintComponent(graphics);
                if(telegramProxy == null)
                    return;
                BufferedImage me = null;
                try {
                    me = telegramProxy.getPhoto(telegramProxy.getMe(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(me == null)
                    me = Images.getLargeUserImage();
                GuiHelper.drawImage(graphics, me, 0, 0, this.getWidth(), this.getHeight());
            }
        };

        closeButton = new JButton() {
            @Override
            protected void paintComponent(Graphics graphics) {
                //super.paintComponent(graphics);
                Graphics2D g2d = (Graphics2D) graphics.create();
                int inset = 5;
                try {
                    g2d.setColor(Color.white);
                    g2d.setStroke(new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));

                    int x = inset;
                    int y = inset;
                    int width = this.getWidth() - inset * 2;
                    int height = this.getHeight() - inset * 2;
                    g2d.drawOval(x, y, width, height);

                    int x1 = width / 4;
                    int x2 = width * 3 / 4;
                    int y1 = height / 4;
                    int y2 = height * 3 / 4;
                    x1 += inset;
                    x2 += inset;
                    y1 += inset;
                    y2 += inset;
                    g2d.drawLine(x1, y1, x2, y2);
                    g2d.drawLine(x1, y2, x2, y1);

                } finally {
                    g2d.dispose();
                }
            }

            @Override
            protected void paintBorder(Graphics graphics) {
                //super.paintBorder(graphics);
            }
        };

        logoutButton = new JButton() {
            @Override
            protected void paintComponent(Graphics graphics) {
                //super.paintComponent(graphics);
                Graphics2D g2d = (Graphics2D) graphics.create();
                int inset = 5;
                try {
                    g2d.setColor(Color.white);
                    g2d.setStroke(new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));

                    int x = inset;
                    int y = inset;
                    int width = this.getWidth() - inset * 2;
                    int height = this.getHeight() - inset * 2;
                    g2d.drawOval(x, y, width, height);

                    int x1 = width / 4;
                    int x2 = width * 3 / 4;
                    int y1 = height / 4;
                    int y2 = height * 3 / 4;
                    int y3 = height / 2;
                    x1 += inset;
                    x2 += inset;
                    y1 += inset;
                    y2 += inset;
                    y3 += inset;
                    g2d.drawLine(x1, y3, x2, y1);
                    g2d.drawLine(x1, y3, x2, y2);

                } finally {
                    g2d.dispose();
                }
            }

            @Override
            protected void paintBorder(Graphics graphics) {
                //super.paintBorder(graphics);
            }
        };
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        //super.paintComponent(graphics);
        Color color = Color.black;
        graphics.setColor(GuiHelper.makeTransparent(color, 0.7f));
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public TelegramProxy getTelegramProxy() {
        return telegramProxy;
    }

    public void setTelegramProxy(TelegramProxy telegramProxy) {
        this.telegramProxy = telegramProxy;
        if(telegramProxy != null) {
            Me me = telegramProxy.getMe();
            nameLabel.setText(me.getFirstName() + " " + me.getLastName());
        } else {
            nameLabel.setText("");
        }
        repaint();
    }

    public void addActionListenerForLogout(ActionListener actionListener) {
        logoutButton.addActionListener(actionListener);
    }

    public void removeActionListenerForLogout(ActionListener actionListener) {
        logoutButton.removeActionListener(actionListener);
    }

    public void addActionListenerForClose(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }

    public void removeActionListenerForClose(ActionListener actionListener) {
        closeButton.removeActionListener(actionListener);
    }
}
