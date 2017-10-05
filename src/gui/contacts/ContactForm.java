package gui.contacts;

import components.GuiHelper;
import components.PhotoPanel;
import gui.Helper;
import org.javagram.dao.Dialog;
import org.javagram.dao.Person;
import org.javagram.dao.proxy.TelegramProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by HerrSergio on 13.05.2016.
 */
public class ContactForm extends JPanel implements ListCellRenderer <Person>{
    private JPanel rootPanel;
    private JLabel nameLabel;
    private JTextPane lastMessageLabel;
    private JPanel photoPanel;
    private JButton button1;



    private boolean hasFocus;

    private final int focusMarkerWidth = 4;

    private Map<Person, Dialog> contactsList;
    private Set<Person> onlineSet;
    private Map<Person, BufferedImage> photos;

    public ContactForm(Map<Person, Dialog> contactsList) {
        this.contactsList = contactsList;

        Helper.clearBoth(lastMessageLabel);

        //Рекомендуемый размер является минимальным
        //Меньше JScrollPane ужимать не будет
        //setPreferredSize(new Dimension(0, 100));
        //А вот setMinimumSize(), вопреки здравому смыслу, вызывать не стоит
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        rootPanel = this;

        photoPanel = new PhotoPanel(null, true, false, 0, false);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(Color.lightGray);
        graphics.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        if(hasFocus) {
            graphics.setColor(Color.blue);
            graphics.fillRect(0/*this.getWidth() - focusMarkerWidth*/, 0, focusMarkerWidth, this.getHeight());
        }
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Person> jList,
                                                  Person person,
                                                  int index, boolean selected, boolean hasFocus) {

        Dialog dialog = contactsList.get(person);
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
        if(photos != null)
            ((PhotoPanel)photoPanel).setImage(Helper.getPhoto(photos, person, true, true));
        else
            ((PhotoPanel)photoPanel).setImage(null);
        if(onlineSet != null)
            ((PhotoPanel)photoPanel).setOnline(onlineSet.contains(person));
        else
            ((PhotoPanel)photoPanel).setOnline(false);


        return this;
    }

    public Set<Person> getOnlineSet() {
        return onlineSet;
    }

    public void setOnlineSet(Set<Person> onlineSet) {
        this.onlineSet = new HashSet<>(onlineSet);
    }

    public Map<Person, BufferedImage> getPhotos() {
        return photos;
    }

    public void setPhotos(Map<Person, BufferedImage> photos) {
        this.photos = new HashMap<>(photos);
    }
}
