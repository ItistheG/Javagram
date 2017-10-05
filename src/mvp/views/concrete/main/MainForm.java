package mvp.views.concrete.main;

import components.MyLayeredPane;
import gui.contacts.ContactsList;
import gui.messsages.MessagesForm;
import gui.overlays.PlusOverlay;
import mvp.views.abs.main.MainView;
import mvp.views.abs.main.MainViewListener;
import org.javagram.dao.Dialog;
import org.javagram.dao.Message;
import org.javagram.dao.Person;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public class MainForm extends gui.main.MainForm implements MainView {

    private ContactsList contactsList = new ContactsList();
    private MyLayeredPane contactsLayeredPane = new MyLayeredPane();
    private PlusOverlay plusOverlay = new PlusOverlay();

    private MainViewListener listener;

    {
        setContactsPanel(contactsLayeredPane);
        contactsLayeredPane.add(contactsList, new Integer(0));
        contactsLayeredPane.add(plusOverlay, new Integer(1));

        contactsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(listener != null && !listSelectionEvent.getValueIsAdjusting())
                    listener.contactSelectionChanged();
            }
        });

        addSendMessageListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listener != null)
                    listener.sendMessage();
            }
        });

        addSearchEventListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listener != null)
                    listener.search();
            }
        });

        plusOverlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listener != null)
                    listener.addContact();
            }
        });

        addBuddyEditEventListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listener != null)
                    listener.editOrDeleteContact();
            }
        });

        addGearEventListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listener != null)
                    listener.showProfile();
            }
        });
    }

    @Override
    public MainViewListener getListener() {
        return listener;
    }

    @Override
    public void setListener(MainViewListener listener) {
        this.listener = listener;
    }

    @Override
    public Person getSelectedValue() {
        return contactsList.getSelectedValue();
    }

    @Override
    public void setSelectedValue(Person person) {
        contactsList.setSelectedValue(person);
    }

    public void setContactsList(Map<Person, Dialog> dialogs) {
        contactsList.setContactsList(dialogs);
    }

    @Override
    public Map<Person, Dialog> getContactsList() {
        return contactsList.getContactsList();
    }

    @Override
    public Set<Person> getOnlineSet() {
        return contactsList.getOnlineSet();
    }

    @Override
    public void setOnlineSet(Set<Person> onlineSet) {
        contactsList.setOnlineSet(onlineSet);
    }

    @Override
    public Map<Person, BufferedImage> getPhotos() {
        return contactsList.getPhotos();
    }

    @Override
    public void setPhotos(Map<Person, BufferedImage> photos) {
        contactsList.setPhotos(photos);
    }

    private MessagesForm createMessagesForm() {
        MessagesForm messagesForm = new MessagesForm();
        setMessagesPanel(messagesForm);
        return messagesForm;
    }

    private MessagesForm getMessagesForm() {
        if (getMessagesPanel() instanceof MessagesForm) {
            return (MessagesForm) getMessagesPanel();
        } else {
            return createMessagesForm();
        }
    }


    public void setMessages(List<Message> messages) {
        getMessagesForm().setMessages(messages);
    }

    public List<Message> getMessages() {
        return getMessagesForm().getMessages();
    }
}
