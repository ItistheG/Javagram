package gui.frame;

import components.*;
import gui.Helper;
import gui.contacts.ContactsList;
import gui.intro.CodeForm;
import gui.intro.PhoneForm;
import gui.intro.Registration;
import gui.main.MainForm;
import gui.messsages.MessagesForm;
import gui.overlays.*;
import gui.resources.Images;
import org.javagram.dao.*;
import org.javagram.dao.Dialog;
import org.javagram.dao.proxy.TelegramProxy;
import org.javagram.dao.proxy.changes.UpdateChanges;
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
    private Registration registrationForm = new Registration();
    private CodeForm codeForm = new CodeForm();
    private MainForm mainForm = new MainForm();
    private ContactsList contactsList = new ContactsList();

    private ProfileForm profileForm = new ProfileForm();
    private AddContactForm addContactForm = new AddContactForm();
    private EditContactForm editContactForm = new EditContactForm();


    private OverlayDialog overlayDialog = new OverlayDialog();

    private MyLayeredPane contactsLayeredPane = new MyLayeredPane();
    private PlusOverlay plusOverlay = new PlusOverlay();

    private Timer timer;
    private int messagesFrozen;

    {
        setTitle("Javagram");
        setIconImage(Images.getAppIcon());

        undecoratedFrame = new Undecorated(this, ComponentResizerAbstract.KEEP_RATIO_CENTER);
        undecoratedFrame.setContentPanel(overlayDialog);

        showPhoneNumberRequest(false);
        setSize(925 + 4, 390 + 39);
        //setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (showQuestionMessage("Уверены, что хотите выйти?", "Вопрос"))
                    exit();
            }
        });

        phoneForm.addActionListenerForConfirm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchFromPhone();
            }
        });

        codeForm.addActionListenerForConfirm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switchFromCode();
            }
        });

        registrationForm.addActionListenerForConfirm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                 switchFromRegistration();
            }
        });

        mainForm.setContactsPanel(contactsLayeredPane);
        contactsLayeredPane.add(contactsList, new Integer(0));
        contactsLayeredPane.add(plusOverlay, new Integer(1));

        contactsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {

                if (listSelectionEvent.getValueIsAdjusting() || messagesFrozen != 0)
                    return;

                if (telegramProxy == null) {
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
                ContactInfo contactInfo = new ContactInfo();
                Person person = contactsList.getSelectedValue();
                if (person instanceof KnownPerson && !(person instanceof Contact))
                    contactInfo.setPhone(((KnownPerson) person).getPhoneNumber());
                addContactForm.setContactInfo(contactInfo);
                changeOverlayPanel(addContactForm);
            }
        });

        addContactForm.addActionListenerForClose(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeOverlayPanel(null);
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
                Person buddy = contactsList.getSelectedValue();
                String text = mainForm.getMessageText().trim();
                if (telegramProxy != null && buddy != null && !text.isEmpty()) {
                    try {
                        telegramProxy.sendMessage(buddy, text);
                        mainForm.setMessageText("");
                        checkForUpdates(true);
                    } catch (Exception e) {
                        showWarningMessage("Не могу отправить сообщение", "Ошибка!");
                    }
                }
            }
        });

        mainForm.addGearEventListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Me me = telegramProxy.getMe();
                ContactInfo contactInfo = Helper.toContactInfo(me, telegramProxy, false, false);
                profileForm.setContactInfo(contactInfo);
                changeOverlayPanel(profileForm);
            }
        });

        profileForm.addActionListenerForClose(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeOverlayPanel(null);
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
                if (person instanceof Contact) {
                    editContactForm.setContactInfo(Helper.toContactInfo((Contact) person, telegramProxy, false, true));
                    changeOverlayPanel(editContactForm);
                }
            }
        });

        editContactForm.addActionListenerForClose(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeOverlayPanel(null);
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
                checkForUpdates(false);
            }
        });
        timer.start();
    }

    public MainFrame(TelegramDAO telegramDAO) throws HeadlessException {
        this.telegramDAO = telegramDAO;
    }

    protected void checkForUpdates(boolean force) {
        if (telegramProxy != null) {
            UpdateChanges updateChanges = telegramProxy.update(force ? TelegramProxy.FORCE_SYNC_UPDATE : TelegramProxy.USE_SYNC_UPDATE);

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
            } else if (updateChanges.getPersonsChanged().getChanged().containsKey(currentBuddy)
                    || updateChanges.getSmallPhotosChanged().contains(currentBuddy)
                    || updateChanges.getLargePhotosChanged().contains(currentBuddy)) {
                displayBuddy(targetPerson);
            }

            if (updateChanges.getPersonsChanged().getChanged().containsKey(telegramProxy.getMe())
                    || updateChanges.getSmallPhotosChanged().contains(telegramProxy.getMe())
                    || updateChanges.getLargePhotosChanged().contains(telegramProxy.getMe())) {
                displayMe(telegramProxy.getMe());
            }
        }
    }

    // Я расширил DebugTelegramDAO, так что теперь номера имеют смысл.
    // Он определется последней цифрой:
    // 0 - NOT_REGISTERED
    // 1 - REGISTERED
    // 2 - INVITED
    // 3 - INVALID
    // 4 - IOException
    // 5-7 - те же 0-2, но код устарел при signIn и Up
    // 8-9 - те же 0,2, но код устарел при signUp (In скажет UNOCCUPIED)

    // Есть два варианта последовательности форм

    //Более сложная

    private void switchFromPhone() {
        String phoneNumber = phoneForm.getPhoneNumber();
        if (phoneNumber == null) {
            showPhoneNumberEmpty();
        } else {
            switchFromPhone(phoneNumber);
        }
    }

    private void switchFromPhone(String phoneNumber) {

        try {
            try {
                telegramDAO.acceptNumber(phoneNumber.replaceAll("[\\D]+", ""));

                if(telegramDAO.canSignUp()) {
                    if(!showQuestionMessage("Пользователь не зарегистрирован. Будет регистрироваться?", "Внимание!")) {
                        showPhoneNumberRequest(true);
                        return;
                    }
                }

                codeForm.setPhoneLabelText(phoneNumber);
                sendAndRequestCode();
            } catch (ApiException e) {
                if (e.isPhoneNumberInvalid()) {
                    showPhoneNumberInvalid();
                    return;
                }
                throw e;
            }
        } catch (Exception e) {
            catchException(e);
        }
    }

    private void switchFromCode() {
        String code = codeForm.getCode();
        if(code == null || code.isEmpty()) {
            showCodeEmpty();
        } else {
            switchFromCode(code);
        }
    }

    private void switchFromCode(String code) {
        try {
            try {
                telegramDAO.signIn(code);
                switchToMainScreen();
            } catch (ApiException e) {
                if (e.isCodeInvalid()) {
                    showCodeInvalid();
                    return;
                }
                if (e.isCodeEmpty()) {
                    showCodeEmpty();
                    return;
                }
                if(e.isCodeExpired()) {
                    showCodeExpired();
                    return;
                }
                if (e.isPhoneNumberUnoccupied()) {
                    showNameRequest(true);
                    return;
                }
                throw e;
            }
        } catch (Exception e) {
            catchException(e);
        }
    }

    private void switchFromRegistration() {
        String firstName = registrationForm.getFirstName();
        String lastName = registrationForm.getLastNameText();
        //Отсекаем только очевидный ляп.
        //С остальным пусть сервер разбирается
        if((firstName == null || firstName.isEmpty())
                && (lastName == null || lastName.isEmpty())) {
            showNameInvalid();
        } else {
            switchFromRegistration(firstName, lastName, codeForm.getCode());
        }
    }

    private void switchFromRegistration(String firstName, String lastName, String code) {
        try {
            try {
                telegramDAO.signUp(code, firstName, lastName);
                switchToMainScreen();
            } catch (ApiException e) {
                if (e.isNameInvalid()) {
                    showNameInvalid();
                    return;
                }
                if(e.isCodeExpired()) {
                    showCodeExpired();
                    return;
                }
                throw e;
            }
        } catch (Exception e) {
            catchException(e);
        }
    }

    //------------------------------------


    //Более простая
    /*
    private void switchFromPhone() {
        String phoneNumber = phoneForm.getPhoneNumber();
        if (phoneNumber == null) {
            showPhoneNumberEmpty();
        } else {
            switchFromPhone(phoneNumber);
        }
    }

    private void switchFromPhone(String phoneNumber) {
        try {
            try {
                telegramDAO.acceptNumber(phoneNumber.replaceAll("[\\D]+", ""));

                if(telegramDAO.canSignUp()) {
                    if(showQuestionMessage("Пользователь не зарегистрирован. Будет регистрироваться?", "Внимание!")) {
                        showNameRequest(true);
                    } else {
                        showPhoneNumberRequest(true);
                        return;
                    }
                } else {
                    sendAndRequestCode();
                }

                codeForm.setPhoneLabelText(phoneNumber);

            } catch (ApiException e) {
                if (e.isPhoneNumberInvalid()) {
                    showPhoneNumberInvalid();
                    return;
                }
                throw e;
            }
        } catch (Exception e) {
            catchException(e);
        }
    }

    private void switchFromRegistration() {
        String firstName = registrationForm.getFirstName();
        String lastName = registrationForm.getLastNameText();
        //Отсекаем только очевидный ляп.
        //С остальным пусть сервер разбирается
        if((firstName == null || firstName.isEmpty())
                && (lastName == null || lastName.isEmpty())) {
            showNameInvalid();
        } else {
            try {
                sendAndRequestCode();
            } catch (Exception e) {
                catchException(e);
            }
        }
    }

    private void switchFromCode() {
        String firstName = registrationForm.getFirstName();
        String lastName = registrationForm.getLastNameText();
        String code = codeForm.getCode();
        if(code == null || code.isEmpty()) {
            showCodeEmpty();
        } else {
            switchFromCode(firstName, lastName, code);
        }
    }

    private void switchFromCode(String firstName, String lastName, String code) {
        try {
            try {
                if(telegramDAO.canSignIn())
                    telegramDAO.signIn(code);
                else
                    telegramDAO.signUp(code, firstName, lastName);
                switchToMainScreen();
            } catch (ApiException e) {
                if (e.isCodeInvalid()) {
                    showCodeInvalid();
                    return;
                }
                if (e.isCodeEmpty()) {
                    showCodeEmpty();
                    return;
                }
                if(e.isCodeExpired()) {
                    showCodeExpired();
                    return;
                }
                if (e.isNameInvalid()) {
                    showNameInvalid();
                    return;
                }
                throw e;
            }
        } catch (Exception e) {
            catchException(e);
        }
    }
    */
    //--------------------------

    private void showPhoneNumberInvalid() {
        showWarningMessage("Номер телефона введен не верно", "Внимание!");
        showPhoneNumberRequest(true);
    }

    private void showPhoneNumberEmpty() {
        showWarningMessage("Введите корректный номер телефона!", "Внимание!");
        showPhoneNumberRequest(true);
    }

    private void showPhoneNumberRequest(boolean clear) {
        changeContentPanel(phoneForm);
        if(clear)
            phoneForm.clear();
        phoneForm.transferFocusTo();
    }

    private void showCodeInvalid() {
        showWarningMessage("Неверный код", "Внимание!");
        showCodeRequest();
    }

    private void showCodeEmpty() {
        showWarningMessage("Не введен код", "Внимание!");
        showCodeRequest();
    }

    private void showCodeExpired() throws IOException, ApiException {
        showWarningMessage("Код устарел. Отправляю новый", "Внимание!");
        sendAndRequestCode();
    }

    private void sendAndRequestCode() throws IOException, ApiException {
        sendCode();
        showCodeRequest();
    }

    private void sendCode() throws IOException, ApiException {
        telegramDAO.sendCode();
    }

    private void showCodeRequest() {
        changeContentPanel(codeForm);
        codeForm.clear();
        codeForm.transferFocusTo();
    }

    private void showNameInvalid() {
        showWarningMessage("Неверные регистрационные данные", "Внимание!");
        showNameRequest(false);
    }

    private void showNameRequest(boolean clear) {
        changeContentPanel(registrationForm);
        if(clear)
            registrationForm.clear();
    }

    private void catchException(Exception e) {
        if (e instanceof IOException) {
            showErrorMessage("Потеряно соединение с сервером", "Ошибка!");
        } else if (e instanceof ApiException) {
            showErrorMessage("Непредвиденная ошибка API :: " + e.getMessage(), "Ошибка!");
        } else {
            showErrorMessage("Непредвиденная ошибка", "Ошибка!");
        }
        abort(e);
    }

    private void switchToMainScreen() throws ApiException {
        changeContentPanel(mainForm);
        createTelegramProxy();
    }

    private void switchToBegin() {
        try {
            destroyTelegramProxy();
            changeOverlayPanel(null);
            showPhoneNumberRequest(true);
            telegramDAO.logOut();
        } catch (Exception e) {
            catchException(e);
        }
    }

    private void searchFor(String text) {
        text = text.trim();
        if (text.isEmpty()) {
            return;
        }
        String[] words = text.toLowerCase().split("\\s+");
        List<Person> persons = telegramProxy.getPersons();
        Person person = contactsList.getSelectedValue();
        person = searchFor(text.toLowerCase(), words, persons, person);
        contactsList.setSelectedValue(person);
        if (person == null)
            showInformationMessage("Ничего не найдено", "Поиск");
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

    private void changeContentPanel(Container contentPanel) {
        overlayDialog.setContentPanel(contentPanel);
    }

    private void changeOverlayPanel(Container overlayPanel) {
        overlayDialog.setOverlayPanel(overlayPanel);
    }

    private void createTelegramProxy() throws ApiException {
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
            displayMe(telegramProxy != null ? telegramProxy.getMe() : null);
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
        if (mainForm.getMessagesPanel() instanceof MessagesForm) {
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
            abort(e);
        }
    }

    private void displayMe(Me me) {
        if (me == null) {
            mainForm.setMeText(null);
            mainForm.setMePhoto(null);
        } else {
            mainForm.setMeText(me.getFirstName() + " " + me.getLastName());
            mainForm.setMePhoto(Helper.getPhoto(telegramProxy, me, true, true));
        }
    }

    private void displayBuddy(Person person) {
        if (person == null) {
            mainForm.setBuddyText(null);
            mainForm.setBuddyPhoto(null);
            mainForm.setBuddyEditEnabled(false);
        } else {
            mainForm.setBuddyText(person.getFirstName() + " " + person.getLastName());
            mainForm.setBuddyPhoto(Helper.getPhoto(telegramProxy, person, true, true));
            mainForm.setBuddyEditEnabled(person instanceof Contact);
        }
    }

    private boolean tryAddContact(ContactInfo info) {

        String phone = info.getClearedPhone();
        if (phone.isEmpty()) {
            showWarningMessage("Пожалуйста, введите номер телефона", "Ошибка");
            return false;
        }
        if (info.getFirstName().isEmpty() && info.getLastName().isEmpty()) {
            showWarningMessage("Пожалуйста, введите имя и/или фамилию", "Ошибка");
            return false;
        }
        for (Person person : telegramProxy.getPersons()) {
            if (person instanceof Contact) {
                if (((Contact) person).getPhoneNumber().replaceAll("\\D+", "").equals(phone)) {
                    showWarningMessage("Контакт с таким номером уже существует", "Ошибка");
                    return false;
                }
            }
        }
        try {
            telegramProxy.importContact(info.getPhone(), info.getFirstName(), info.getLastName());
        } catch (Exception e) {
            showWarningMessage("Ошибка на сервере при добавлении контакта", "Ошибка");
            return false;
        }

        changeOverlayPanel(null);
        checkForUpdates(true);
        return true;
    }

    private boolean tryUpdateContact(ContactInfo info) {

        String phone = info.getClearedPhone();

        if (info.getFirstName().isEmpty() && info.getLastName().isEmpty()) {
            showWarningMessage("Пожалуйста, введите имя и/или фамилию", "Ошибка");
            return false;
        }

        try {
            telegramProxy.importContact(info.getPhone(), info.getFirstName(), info.getLastName());
        } catch (Exception e) {
            showWarningMessage("Ошибка на сервере при изменении контакта", "Ошибка");
            return false;
        }

        changeOverlayPanel(null);
        checkForUpdates(true);
        return true;
    }

    private boolean tryDeleteContact(ContactInfo info) {
        int id = info.getId();

        try {
            telegramProxy.deleteContact(id);
        } catch (Exception e) {
            showWarningMessage("Ошибка на сервере при удалении контакта", "Ошибка");
            return false;
        }

        changeOverlayPanel(null);
        checkForUpdates(true);
        return true;
    }

    private void abort(Throwable e) {
        if (e != null)
            e.printStackTrace();
        else
            System.err.println("Unknown Error");
        try {
            telegramDAO.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.exit(-1);
    }

    private void exit() {
        try {
            telegramDAO.close();
            System.exit(0);
        } catch (Exception e) {
            abort(e);
        }

    }

    //Альтернативное решение
    //private BlueButton[] okButton = BlueButton.createButtons(JOptionPane.DEFAULT_OPTION);
    //private BlueButton[] yesNoButtons = BlueButton.createButtons(JOptionPane.YES_NO_OPTION);

    private JButton[] okButton = BlueButton.createDecoratedButtons(JOptionPane.DEFAULT_OPTION);
    private JButton[] yesNoButtons = BlueButton.createDecoratedButtons(JOptionPane.YES_NO_OPTION);

    private void showErrorMessage(String text, String title) {
        Undecorated.showDialog(this, text, title, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, Images.getErrorIcon(),
                okButton, okButton[0]);
    }

    private void showWarningMessage(String text, String title) {
        Undecorated.showDialog(this, text, title, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, Images.getWarningIcon(),
                okButton, okButton[0]);
    }

    private void showInformationMessage(String text, String title) {
        Undecorated.showDialog(this, text, title, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, Images.getInformationIcon(),
                okButton, okButton[0]);
    }

    private boolean showQuestionMessage(String text, String title) {
        return Undecorated.showDialog(this, text, title, JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION, Images.getQuestionIcon(),
                yesNoButtons, yesNoButtons[0]) == 0;
    }

}
