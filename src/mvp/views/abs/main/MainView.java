package mvp.views.abs.main;

import gui.contacts.ContactForm;
import mvp.views.abs.ContentView;
import org.javagram.dao.Dialog;
import org.javagram.dao.Message;
import org.javagram.dao.Person;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public interface MainView extends ContentView {
    void setListener(MainViewListener listener);
    MainViewListener getListener();

    Person getSelectedValue();
    void setSelectedValue(Person person);
    void setContactsList(Map<Person, Dialog> dialogs);
    Map<Person, Dialog> getContactsList();
    Set<Person> getOnlineSet();
    void setOnlineSet(Set<Person> onlineSet);
    Map<Person, BufferedImage> getPhotos();
    void setPhotos(Map<Person, BufferedImage> photos);

    void setMessages(List<Message> messages);
    List<Message> getMessages();

    String getSearchText();

    String getMessageText();

    void setMessageText(String text);


    String getMeText();
    void setMeText(String meText);

    BufferedImage getMePhoto();
    void setMePhoto(BufferedImage mePhoto) ;

    String getBuddyText();
    void setBuddyText(String buddyText);

    BufferedImage getBuddyPhoto();
    void setBuddyPhoto(BufferedImage buddyPhoto);

    boolean isBuddySelected();
    void setBuddySelected(boolean buddySelected) ;

    boolean isBuddyEditEnabled();
    void setBuddyEditEnabled(boolean enabled);
}
