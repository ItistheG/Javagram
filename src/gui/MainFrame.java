package gui;

import components.GuiHelper;
import contacts.ContactsList;
import messsages.MessagesForm;
import org.javagram.dao.*;
import org.javagram.dao.Dialog;
import org.javagram.dao.proxy.TelegramProxy;
import org.javagram.dao.proxy.changes.UpdateChanges;
import overlays.*;
import resources.Images;
import undecorated.ComponentResizerAbstract;
import undecorated.Undecorated;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


/**
 * Created by HerrSergio on 05.04.2016.
 */
public class MainFrame extends JFrame {

    private Undecorated undecoratedFrame;

    private TelegramDAO telegramDAO;
    private TelegramProxy telegramProxy;

    private PhoneForm phoneForm = new PhoneForm();
    private CodeForm codeForm = new CodeForm();
    private MainForm mainForm = new MainForm();
    private ContactsList contactsList = new ContactsList();

    private ProfileForm profileForm = new ProfileForm();
    private AddContactForm addContactForm = new AddContactForm();
    private EditContactForm editContactForm = new EditContactForm();
    private MyBufferedOverlayDialog mainWindowManager = new MyBufferedOverlayDialog(mainForm, profileForm, addContactForm, editContactForm);
    private static final int MAIN_WINDOW = -1, PROFILE_FORM = 0, ADD_CONTACT_FORM = 1, EDIT_CONTACT_FORM = 2;

    private MyLayeredPane contactsLayeredPane = new MyLayeredPane();
    private PlusOverlay plusOverlay = new PlusOverlay();

    private Timer timer;
    private int messagesFrozen;

    {
        setTitle("Javagram");
        undecoratedFrame = new Undecorated(this, ComponentResizerAbstract.KEEP_RATIO_CENTER);

        changeContentPanel(phoneForm);
        setSize(925 + 4, 390 + 39);
        //setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if(showQuestionMessage("Уверены, что хотите выйти?", "Вопрос"))
                    exit();
            }
        });

        phoneForm.addActionListenerForConfirm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               String phoneNumber = phoneForm.getPhoneNumber();
                if(phoneNumber == null) {
                    showErrorMessage("Введите корректный номер телефона!", "Ошибка!");
                } else {
                    switchFromPhoneToCode(phoneNumber);
                }
            }
        });

        codeForm.addActionListenerForConfirm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String authCode = codeForm.getCode();
                switchFromCodeToMain(authCode);
            }
        });

        mainForm.setContactsPanel(contactsLayeredPane);
        contactsLayeredPane.add(contactsList, new Integer(0));
        contactsLayeredPane.add(plusOverlay,new Integer(1));

        contactsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {

                if(messagesFrozen != 0)
                    return;

                if(telegramProxy == null)  {
                    displayDialog(null);
                } else {
                    displayDialog(contactsList.getSelectedValue());
                }
            }
        });

        mainForm.addSearchEventListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                searchFor(mainForm.getSearchText());
            }
        });

        plusOverlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addContactForm.setContactInfo(new ContactInfo());
                mainWindowManager.setIndex(ADD_CONTACT_FORM);
            }
        });

        addContactForm.addActionListenerForClose(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainWindowManager.setIndex(MAIN_WINDOW);
            }
        });

        addContactForm.addActionListenerForAdd(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tryAddContact(addContactForm.getContactInfo());
            }
        });

        mainForm.addSendMessageListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Person buddy =  contactsList.getSelectedValue();
                String text = mainForm.getMessageText().trim();
                if(telegramProxy != null && buddy != null && !text.isEmpty()) {
                    try {
                        telegramProxy.sendMessage(buddy, text);
                        mainForm.setMessageText("");
                        checkForUpdates();
                    } catch (Exception e) {
                        showErrorMessage("Не могу отправить сообщение", "Ошибка!");
                    }
                }
            }
        });

        mainForm.addGearEventListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                profileForm.setTelegramProxy(telegramProxy);
                mainWindowManager.setIndex(PROFILE_FORM);
            }
        });

        profileForm.addActionListenerForClose(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainWindowManager.setIndex(MAIN_WINDOW);
            }
        });

        profileForm.addActionListenerForLogout(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchToBegin();
            }
        });

        mainForm.addBuddyEditEventListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Person person = contactsList.getSelectedValue();
                if(person instanceof Contact) {
                    editContactForm.setContactInfo(new ContactInfo((Contact) person));
                    editContactForm.setPhoto(GuiHelper.getPhoto(telegramProxy, person, false));
                    mainWindowManager.setIndex(EDIT_CONTACT_FORM);
                }
            }
        });

        editContactForm.addActionListenerForClose(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainWindowManager.setIndex(MAIN_WINDOW);
            }
        });

        editContactForm.addActionListenerForSave(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tryUpdateContact(editContactForm.getContactInfo());
            }
        });

        editContactForm.addActionListenerForRemove(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tryDeleteContact(editContactForm.getContactInfo());
            }
        });

        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                checkForUpdates();
            }
        });
        timer.start();
    }

    public MainFrame(TelegramDAO telegramDAO) throws HeadlessException {
        this.telegramDAO = telegramDAO;
    }

    protected void checkForUpdates() {
        if (telegramProxy != null) {
            UpdateChanges updateChanges = telegramProxy.update();

            int photosChangedCount = updateChanges.getLargePhotosChanged().size() +
                    updateChanges.getSmallPhotosChanged().size() +
                    updateChanges.getStatusesChanged().size();

            if (updateChanges.getListChanged()) {
                updateContacts();
            } else if (photosChangedCount != 0) {
                contactsList.repaint();
            }

            Person currentBuddy = getMessagesForm().getPerson();
            Person targetPerson = contactsList.getSelectedValue();

            Dialog currentDialog = currentBuddy != null ? telegramProxy.getDialog(currentBuddy) : null;

            if (!Objects.equals(targetPerson, currentBuddy) ||
                    updateChanges.getDialogsToReset().contains(currentDialog) ||
                    //updateChanges.getDialogsChanged().getChanged().containsKey(currentDialog) ||
                    updateChanges.getDialogsChanged().getDeleted().contains(currentDialog)) {
                updateMessages();
            } else if(updateChanges.getPersonsChanged().getChanged().containsKey(currentBuddy)
                    || updateChanges.getSmallPhotosChanged().contains(currentBuddy)
                    || updateChanges.getLargePhotosChanged().contains(currentBuddy)) {
                displayBuddy(targetPerson);
            }

            if(updateChanges.getPersonsChanged().getChanged().containsKey(telegramProxy.getMe())
                    || updateChanges.getSmallPhotosChanged().contains(telegramProxy.getMe())
                    || updateChanges.getLargePhotosChanged().contains(telegramProxy.getMe())) {
                displayMe(telegramProxy.getMe());
            }
        }
    }


    private void switchFromPhoneToCode(String phoneNumber) {

        try {
            telegramDAO.acceptNumber(phoneNumber.replaceAll("[\\D]+", ""));
        } catch (IOException | NullPointerException e) {
            showErrorMessage("Номер телефона введене неверно", "Ошибка!");
            return;
        }

        if(telegramDAO.canSignIn()) {
            try {
                telegramDAO.sendCode();
                codeForm.setPhoneLabelText(phoneNumber);
                changeContentPanel(codeForm);
            } catch (Exception e) {
                showErrorMessage("Потеряно соединение с сервером", "Ошибка!");
                changeContentPanel(phoneForm);
                return;
            }
        } else if(telegramDAO.canSignUp()) {
            showWarningMessage("Пользователь незарегистрирован", "Внимание!");
            return;
        } else {
            abort(null);
        }
    }

    private void switchFromCodeToMain(String code) {
        try {
            telegramDAO.signIn(code);
            changeContentPanel(mainWindowManager);
            createTelegramProxy();
        } catch (Exception e) {
            showWarningMessage("Неверный код", "Внимание!");
        }
    }


    private void switchToBegin() {
        try {
            destroyTelegramProxy();
            this.codeForm.clear();
            this.phoneForm.clear();
            mainWindowManager.setIndex(MAIN_WINDOW);
            changeContentPanel(phoneForm);
            telegramDAO.logOut();
        } catch (Exception e) {
            showErrorMessage("Продолжение работы не возможно", "Критическая ошибка!");
            abort(e);
        }
    }

    private void searchFor(String text) {
        text = text.trim();
        if(text.isEmpty()) {
            return;
        }
        String[] words = text.toLowerCase().split("\\s+");
        List<Person> persons = telegramProxy.getPersons();
        Person person = contactsList.getSelectedValue();
        person = searchFor(text.toLowerCase(), words, persons, person);
        contactsList.setSelectedValue(person);
        if(person == null)
            showInformationMessage("Ничего не найдено", "Поиск");
    }

    private static Person searchFor(String text, String[] words, List<? extends Person> persons, Person current) {
        int currentIndex = persons.indexOf(current);

        for(int i = 1; i <= persons.size(); i++) {
            int index = (currentIndex + i) % persons.size();
            Person person = persons.get(index);
            if(contains(person.getFirstName().toLowerCase(), words)
                    || contains(person.getLastName().toLowerCase(), words)) {
                return person;
            }
        }
        return null;
    }

    private static boolean contains(String text, String... words) {
        for(String word : words) {
            if(text.contains(word))
                return true;
        }
        return false;
    }

    private void changeContentPanel(Container contentPanel) {
        undecoratedFrame.setContentPanel(contentPanel);
    }

    private void createTelegramProxy() {
        telegramProxy = new TelegramProxy(telegramDAO);
        updateTelegramProxy();
    }

    private void destroyTelegramProxy() {
        telegramProxy = null;
        updateTelegramProxy();
    }

    private void updateTelegramProxy() {
        messagesFrozen++;
        try {
            contactsList.setTelegramProxy(telegramProxy);
            contactsList.setSelectedValue(null);
            createMessagesForm();
            displayDialog(null);
            displayMe(telegramProxy!= null ? telegramProxy.getMe() : null);
        } finally {
            messagesFrozen--;
        }

        mainForm.revalidate();
        mainForm.repaint();
    }

    private void updateContacts() {
        messagesFrozen++;
        try {
            Person person = contactsList.getSelectedValue();
            contactsList.setTelegramProxy(telegramProxy);
            contactsList.setSelectedValue(person);
        } finally {
            messagesFrozen--;
        }
    }

    private void updateMessages() {
        displayDialog(contactsList.getSelectedValue());
        mainForm.revalidate();
        mainForm.repaint();
    }

    private MessagesForm createMessagesForm() {
        MessagesForm messagesForm = new MessagesForm(telegramProxy);
        mainForm.setMessagesPanel(messagesForm);
        mainForm.revalidate();
        mainForm.repaint();
        return messagesForm;
    }

    private MessagesForm getMessagesForm() {
        if(mainForm.getMessagesPanel() instanceof MessagesForm) {
            return (MessagesForm) mainForm.getMessagesPanel();
        } else {
            return createMessagesForm();
        }
    }

    private void displayDialog(Person person) {
        try {
            MessagesForm messagesForm = getMessagesForm();
            messagesForm.display(person);
            displayBuddy(person);
            revalidate();
            repaint();
        } catch (Exception e) {
            showErrorMessage("Проблема соединения с сервером", "проблемы в сети");
        }
    }

    private void displayMe(Me me) {
        if(me == null) {
            mainForm.setMeText(null);
            mainForm.setMePhoto(null);
        } else {
            mainForm.setMeText(me.getFirstName() + " " + me.getLastName());
            try {
                BufferedImage meImage = telegramProxy.getPhoto(me, true);
                if (meImage == null)
                    mainForm.setMePhoto(Images.getSmallUserImage());
                else
                    mainForm.setMePhoto(meImage);
            } catch (IOException e) {
                mainForm.setMePhoto(Images.getSmallUserImage());
                e.printStackTrace();
            }
        }
    }

    private void displayBuddy(Person person) {
        if(person == null) {
            mainForm.setBuddyText(null);
            mainForm.setBuddyPhoto(null);
            mainForm.setBuddyEditEnabled(false);
        } else {
            mainForm.setBuddyText(person.getFirstName() + " " + person.getLastName());
            try {
                BufferedImage buddyImage = telegramProxy.getPhoto(person, true);
                if (buddyImage == null)
                    mainForm.setBuddyPhoto(Images.getSmallUserImage());
                else
                    mainForm.setBuddyPhoto(buddyImage);
            } catch (IOException e) {
                mainForm.setBuddyPhoto(Images.getSmallUserImage());
                e.printStackTrace();
            }
            mainForm.setBuddyEditEnabled(person instanceof Contact);
        }
    }

    private boolean tryAddContact(ContactInfo info) {

        String phone = info.getClearedPhone() ;
        if(phone.isEmpty()) {
            showWarningMessage("Пожалуйста, введите номер телефона", "Ошибка");
            return false;
        }
        if(info.getFirstName().isEmpty() && info.getLastName().isEmpty()) {
            showWarningMessage("Пожалуйста, введите имя и/или фамилию", "Ошибка");
            return false;
        }
        for(Person person : telegramProxy.getPersons()) {
            if(person instanceof Contact) {
                if(((Contact) person).getPhoneNumber().replaceAll("\\D+", "").equals(phone)) {
                    showWarningMessage("Контакт с таким номером уже существует", "Ошибка");
                    return false;
                }
            }
        }

        if(!telegramProxy.importContact(info.getPhone(), info.getFirstName(), info.getLastName())) {
            showErrorMessage("Ошибка на сервере при добавлении контакта", "Ошибка");
            return  false;
        }

        mainWindowManager.setIndex(MAIN_WINDOW);
        checkForUpdates();
        return true;
    }

    private boolean tryUpdateContact(ContactInfo info) {
        String phone = info.getClearedPhone() ;

        if(info.getFirstName().isEmpty() && info.getLastName().isEmpty()) {
            showWarningMessage("Пожалуйста, введите имя и/или фамилию", "Ошибка");
            return false;
        }

        if(!telegramProxy.importContact(info.getPhone(), info.getFirstName(), info.getLastName())) {
            showErrorMessage("Ошибка на сервере при изменении контакта", "Ошибка");
            return  false;
        }

        mainWindowManager.setIndex(MAIN_WINDOW);
        checkForUpdates();
        return true;
    }

    private boolean tryDeleteContact(ContactInfo info) {
       int id = info.getId();

        if(!telegramProxy.deleteContact(id)) {
            showErrorMessage("Ошибка на сервере при удалении контакта", "Ошибка");
            return  false;
        }

        mainWindowManager.setIndex(MAIN_WINDOW);
        checkForUpdates();
        return true;
    }

    private void abort(Exception e) {
        if(e != null)
            e.printStackTrace();
        else
            System.err.println("Unknown Error");
        telegramDAO.close();
        System.exit(-1);
    }

    private void exit() {
        telegramDAO.close();
        System.exit(0);
    }




    private void showErrorMessage(String text, String title) {
        Undecorated.showDialog(this, text, title, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
    }

    private void showWarningMessage(String text, String title) {
        Undecorated.showDialog(this, text, title, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION);
    }

    private void showInformationMessage(String text, String title) {
        Undecorated.showDialog(this, text, title, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
    }

    private boolean showQuestionMessage(String text, String title) {
        return Undecorated.showDialog(this, text, title, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

}
