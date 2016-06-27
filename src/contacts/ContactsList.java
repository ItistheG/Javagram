package contacts;

import components.GuiHelper;
import org.javagram.dao.Person;
import org.javagram.dao.proxy.TelegramProxy;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

/**
 * Created by HerrSergio on 26.05.2016.
 */
public class ContactsList extends JPanel {
    private JPanel rootPanel;
    private JList<Person> list;
    private JScrollPane scrollPane;

    private TelegramProxy telegramProxy;

    {
        GuiHelper.decorateScrollPane(scrollPane);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;
    }

    public TelegramProxy getTelegramProxy() {
        return telegramProxy;
    }

    public void setTelegramProxy(TelegramProxy telegramProxy) {
        this.telegramProxy = telegramProxy;

        if(telegramProxy != null) {
            java.util.List<Person> dialogs = telegramProxy.getPersons();
            list.setCellRenderer(new ContactForm(telegramProxy));
            list.setListData(dialogs.toArray(new Person[dialogs.size()]));
        } else {
            list.setCellRenderer(null);
            list.setListData(new Person[0]);
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
