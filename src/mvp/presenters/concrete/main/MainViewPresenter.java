package mvp.presenters.concrete.main;

import gui.Helper;
import mvp.controller.abs.Controller;
import mvp.model.abs.TelegramModel;
import mvp.presenters.abs.main.MainPresenter;
import mvp.presenters.concrete.PresenterHelper;
import mvp.views.abs.main.MainView;
import mvp.views.abs.main.MainViewListener;
import org.javagram.dao.Contact;
import org.javagram.dao.Dialog;
import org.javagram.dao.Me;
import org.javagram.dao.Person;
import org.javagram.dao.proxy.changes.UpdateChanges;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public class MainViewPresenter implements MainPresenter, MainViewListener, Observer, PropertyChangeListener {
    private MainView view;
    private Controller controller;
    private TelegramModel model;

    private final int MESSAGE_COUNT = 100;

    public MainViewPresenter(Controller controller) {
        this.controller = controller;
        view = controller.getMainView();
        model = controller.getModel();
        view.setListener(this);
        model.addObserver(this);
        model.addSelectedContactIdChangeListener(this);
    }

    @Override
    public void start() {
        controller.getRootView().setContentView(view);
        displayContacts();
        displayContactsStatuses();
        displayContactsPhotos();
        displayMe(model.getMe());
        displayDialog(null);
    }

    public void displayContactsStatuses() {
        view.setOnlineSet(model.online());
    }

    public void displayContactsPhotos() {
        view.setPhotos(model.getPhotosNoThrow(true));
    }

    public void displayContacts() {
        view.setContactsList(model.getDialogs(true));
    }

    private void displayMe(Me me) {
        if (me == null) {
            view.setMeText(null);
            view.setMePhoto(null);
        } else {
            view.setMeText(PresenterHelper.getPersonName(me));
            view.setMePhoto(model.getPhotoNoThrow(model.getMe(), true));
        }
    }

    private void displayBuddy(Person person) {
        if (person == null) {
            view.setBuddySelected(false);
            view.setBuddyText(null);
            view.setBuddyPhoto(null);
            view.setBuddyEditEnabled(false);
        } else {
            view.setBuddySelected(true);
            view.setBuddyText(PresenterHelper.getPersonName(person));
            view.setBuddyPhoto(model.getPhotoNoThrow(person, true));
            view.setBuddyEditEnabled(person instanceof Contact);
        }
    }

    private void displayMessages(Person person) {
        if (person == null) {
            view.setMessages(null);
        } else {
            view.setMessages(model.getMessages(person, MESSAGE_COUNT));
        }
    }

    private void displayDialog(Person selectedPerson) {
        displayBuddy(selectedPerson);
        displayMessages(selectedPerson);
    }

    @Override
    public void contactSelectionChanged() {
        Person selectedPerson = view.getSelectedValue();
        if(selectedPerson != null)
            model.setSelectedContactId(selectedPerson.getId());
        else
            model.setSelectedContactId(null);
    }

    @Override
    public void sendMessage() {
        Person buddy = PresenterHelper.getSelectedPerson(model);
        String text = view.getMessageText().trim();
        if ( buddy != null && !text.isEmpty()) {
            try {
                model.sendMessage(buddy, text);
                view.setMessageText("");
                model.update(true);
            } catch (Exception e) {
                controller.getRootView().showWarningMessage("Не могу отправить сообщение", "Ошибка!");
            }
        }
    }

    @Override
    public void search() {
        String text = view.getSearchText();
        text = text.trim();
        if (text.isEmpty()) {
            return;
        }
        String[] words = text.toLowerCase().split("\\s+");
        List<Person> persons = model.getPersons();
        Person person = PresenterHelper.getSelectedPerson(model);
        person = searchFor(text.toLowerCase(), words, persons, person);
        if (person == null) {
            model.setSelectedContactId(null);
            controller.getRootView().showInformationMessage("Ничего не найдено", "Поиск");
        } else {
            model.setSelectedContactId(person.getId());
        }

    }

    @Override
    public void addContact() {
        controller.getAddContactPresenter().start();
    }

    @Override
    public void editOrDeleteContact() {
        controller.getEditDeleteContactPresenter().start();
    }

    @Override
    public void showProfile() {
        controller.getProfilePresenter().start();
    }

    private static Person searchFor(String text, String[] words, List<? extends Person> persons, Person current) {
        int currentIndex = persons.indexOf(current);

        for (int i = 1; i <= persons.size(); i++) {
            int index = (currentIndex + i) % persons.size();
            Person person = persons.get(index);
            if (contains(person.getFirstName().toLowerCase(), words)
                    || contains(person.getLastName().toLowerCase(), words)) {
                return person;
            }
        }
        return null;
    }

    private static boolean contains(String text, String... words) {
        for (String word : words) {
            if (text.contains(word))
                return true;
        }
        return false;
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof UpdateChanges) {

            UpdateChanges updateChanges = (UpdateChanges) o;

            int photosChangedCount = updateChanges.getLargePhotosChanged().size() +
                    updateChanges.getSmallPhotosChanged().size();
            int statusesChangedCount = updateChanges.getStatusesChanged().size();

            if (updateChanges.getListChanged()) {
                displayContacts();
            } else {
                if (photosChangedCount != 0) {
                    displayContactsPhotos();
                }
                if (statusesChangedCount != 0) {
                    displayContactsStatuses();
                }
            }

            Person currentBuddy = PresenterHelper.getSelectedPerson(model);

            if (currentBuddy != null) {
                Dialog currentDialog = model.getDialog(currentBuddy);

                if (//!Objects.equals(targetPerson, currentBuddy) ||
                        updateChanges.getDialogsToReset().contains(currentDialog) ||
                                //updateChanges.getDialogsChanged().getChanged().containsKey(currentDialog) ||
                                updateChanges.getDialogsChanged().getDeleted().contains(currentDialog)) {
                    displayMessages(currentBuddy);
                } else if (updateChanges.getPersonsChanged().getChanged().containsKey(currentBuddy)
                        || updateChanges.getSmallPhotosChanged().contains(currentBuddy)
                        || updateChanges.getLargePhotosChanged().contains(currentBuddy)) {
                    displayBuddy(currentBuddy);
                }
            }

            if (updateChanges.getPersonsChanged().getChanged().containsKey(model.getMe())
                    || updateChanges.getSmallPhotosChanged().contains(model.getMe())
                    || updateChanges.getLargePhotosChanged().contains(model.getMe())) {
                displayMe(model.getMe());
            }

        }
    }

    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if(propertyChangeEvent.getPropertyName().equals("selectedContactId")) {
            Integer id = (Integer) propertyChangeEvent.getNewValue();
            if (id == null) {
                displayDialog(null);
                view.setSelectedValue(null);
            } else {
                Person person = model.getPerson(model.getSelectedContactId());
                displayDialog(person);
                view.setSelectedValue(person);
            }
        }
    }
}
