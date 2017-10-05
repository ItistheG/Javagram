package gui.contacts;

import components.GuiHelper;
import org.javagram.dao.Dialog;
import org.javagram.dao.Person;
import org.javagram.dao.proxy.TelegramProxy;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by HerrSergio on 26.05.2016.
 */
public class ContactsList extends JPanel {
    private JPanel rootPanel;
    private JList<Person> list;
    private JScrollPane scrollPane;

   // private TelegramProxy telegramProxy;

    {
        GuiHelper.decorateScrollPane(scrollPane);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;
    }

  /*  public TelegramProxy getTelegramProxy() {
        return telegramProxy;
    }

    public void setTelegramProxy(TelegramProxy telegramProxy) {
        this.telegramProxy = telegramProxy;


    }*/

    private Map<Person, Dialog> contactsList;

    public void setContactsList(Map<Person, Dialog> dialogs) {
        if(dialogs != null) {
            contactsList = new LinkedHashMap<>(dialogs);
            List<Person> persons = new ArrayList<>(contactsList.keySet());
            list.setCellRenderer(new ContactForm(contactsList));
            list.setListData(persons.toArray(new Person[persons.size()]));
        } else {
            list.setCellRenderer(new DefaultListCellRenderer());
            list.setListData(new Person[0]);
            contactsList = null;
        }

    }



    public Map<Person, Dialog> getContactsList() {
        return contactsList;
    }

    public Set<Person> getOnlineSet() {
        ListCellRenderer listCellRenderer = list.getCellRenderer();
        if(listCellRenderer instanceof ContactForm)
            return ((ContactForm) listCellRenderer).getOnlineSet();
        else
            return null;
    }

    public void setOnlineSet(Set<Person> onlineSet) {
        ListCellRenderer listCellRenderer = list.getCellRenderer();
        if(listCellRenderer instanceof ContactForm) {
            ((ContactForm) listCellRenderer).setOnlineSet(onlineSet);
            repaint();
        }
    }

    public Map<Person, BufferedImage> getPhotos() {
        ListCellRenderer listCellRenderer = list.getCellRenderer();
        if(listCellRenderer instanceof ContactForm)
            return ((ContactForm) listCellRenderer).getPhotos();
        else
            return null;
    }

    public void setPhotos(Map<Person, BufferedImage> photos) {
        ListCellRenderer listCellRenderer = list.getCellRenderer();
        if(listCellRenderer instanceof ContactForm) {
            ((ContactForm) listCellRenderer).setPhotos(photos);
            repaint();
        }
    }

    public void addListSelectionListener(ListSelectionListener listSelectionListener)  {
        list.addListSelectionListener(listSelectionListener);
    }

    public void removeListSelectionListener(ListSelectionListener listSelectionListener)  {
        list.removeListSelectionListener(listSelectionListener);
    }

    public Person getSelectedValue() {
        return list.getSelectedValue();
    }

    public void setSelectedValue(Person person) {
        if(person != null) {
            ListModel<Person> model = list.getModel();
            for (int i = 0; i < model.getSize(); i++) {
                if (model.getElementAt(i).equals(person)) {
                    list.setSelectedIndex(i);
                    return;
                }
            }
        }
        list.clearSelection();
    }
}
