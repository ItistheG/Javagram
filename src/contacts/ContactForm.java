package contacts;

import org.javagram.dao.Person;
import org.javagram.dao.Dialog;
import org.javagram.dao.proxy.TelegramProxy;
import resources.Images;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * Created by HerrSergio on 13.05.2016.
 */
public class ContactForm extends JPanel implements ListCellRenderer <Person>{
    private JPanel rootPanel;
    private JLabel nameLabel;
    private JTextPane lastMessageLabel;
    private JPanel photoPanel;
    private JButton button1;

    private TelegramProxy telegramProxy;
    private Person person;
    private boolean hasFocus;

    private final int focusMarkerWidth = 4;
    private final double onlineSignSize = 0.3;

    public ContactForm(TelegramProxy telegramProxy) {
        this.telegramProxy = telegramProxy;

        //Рекомендуемый размер является минимальным
        //Меньше JScrollPane ужимать не будет
        //setPreferredSize(new Dimension(0, 100));
        //А вот setMinimumSize(), вопреки здравому смыслу, вызывать не стоит
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        rootPanel = this;

        photoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                boolean small = true;
                BufferedImage image;

                try {
                    image = telegramProxy.getPhoto(person, small);
                } catch (Exception e) {
                    e.printStackTrace();
                    image = null;
                }

                if(image == null)
                    image = Images.getUserImage(small);

                graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);

                if(telegramProxy.isOnline(person)) {

                    int dx = (int)(this.getWidth() * onlineSignSize);
                    int dy = (int)(this.getHeight() * onlineSignSize);

                    int x = this.getWidth() - dx;
                    int y = this.getHeight() - dy;

                    dx -= 2;
                    dy -= 2;

                    graphics.setColor(new Color(0x00B000));
                    graphics.fillRoundRect(x, y, dx, dy, dx, dy);

                    graphics.setColor(new Color(0x0000B0));
                    graphics.drawRoundRect(x, y, dx, dy, dx, dy);
                }
            }
        };
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if(hasFocus) {
            graphics.setColor(Color.blue);
            graphics.fillRect(0/*this.getWidth() - focusMarkerWidth*/, 0, focusMarkerWidth, this.getHeight());
        }
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Person> jList,
                                                  Person person,
                                                  int index, boolean selected, boolean hasFocus) {

        this.person = person;
        Dialog dialog = telegramProxy.getDialog(person);
        this.nameLabel.setText(person.getFirstName() + " " + person.getLastName());

        if(dialog != null){
            this.lastMessageLabel.setText(dialog.getLastMessage().getText());
        } else {
            this.lastMessageLabel.setText("");
        }

        if(selected)
            setBackground(Color.white);
        else {
            setBackground(Color.lightGray);
        }

        this.hasFocus = hasFocus;

        return this;
    }

}
