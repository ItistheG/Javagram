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

    private OverlayDialog overlayDialog = new OverlayDialog();

    {
        setTitle("Javagram");
        setIconImage(Images.getAppIcon());

        undecoratedFrame = new Undecorated(this, ComponentResizerAbstract.KEEP_RATIO_CENTER);
        undecoratedFrame.setContentPanel(overlayDialog);

        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                MainFrame.this.windowClosing(windowEvent);
            }
        });
    }

    protected void setContentPanel(Component contentPanel) {
        overlayDialog.setContentPanel(contentPanel);
    }

    protected Component getContentPanel() {
        return overlayDialog.getContentPanel();
    }

    protected void setOverlayPanel(Component overlayPanel) {
        overlayDialog.setOverlayPanel(overlayPanel);
    }

    protected Component getOverlayPanel() {
        return overlayDialog.getOverlayPanel();
    }

    //Альтернативное решение
    //private BlueButton[] okButton = BlueButton.createButtons(JOptionPane.DEFAULT_OPTION);
    //private BlueButton[] yesNoButtons = BlueButton.createButtons(JOptionPane.YES_NO_OPTION);

    private JButton[] okButton = BlueButton.createDecoratedButtons(JOptionPane.DEFAULT_OPTION);
    private JButton[] yesNoButtons = BlueButton.createDecoratedButtons(JOptionPane.YES_NO_OPTION);

    protected void showErrorMessage(String text, String title) {
        Undecorated.showDialog(this, text, title, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, Images.getErrorIcon(),
                okButton, okButton[0]);
    }

    protected void showWarningMessage(String text, String title) {
        Undecorated.showDialog(this, text, title, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, Images.getWarningIcon(),
                okButton, okButton[0]);
    }

    protected void showInformationMessage(String text, String title) {
        Undecorated.showDialog(this, text, title, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, Images.getInformationIcon(),
                okButton, okButton[0]);
    }

    protected boolean showQuestionMessage(String text, String title) {
        return Undecorated.showDialog(this, text, title, JOptionPane.QUESTION_MESSAGE, JOptionPane.DEFAULT_OPTION, Images.getQuestionIcon(),
                yesNoButtons, yesNoButtons[0]) == 0;
    }

    public void showView() {
        setSize(925 + 4, 390 + 39);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void hideView() {
        setVisible(false);
    }

    protected void windowClosing(WindowEvent windowEvent) {

    }








    private int messagesFrozen;

    {

        if (false) {
            //showPhoneNumberRequest(false);

            //setSize(800, 600);






          /*  contactsList.addListSelectionListener(new ListSelectionListener() {
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
                    setOverlayPanel(addContactForm);
                }
            });

            addContactForm.addActionListenerForClose(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setOverlayPanel(null);
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
                    setOverlayPanel(profileForm);
                }
            });

            profileForm.addActionListenerForClose(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setOverlayPanel(null);
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
                        setOverlayPanel(editContactForm);
                    }
                }
            });

            editContactForm.addActionListenerForClose(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setOverlayPanel(null);
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
            timer.start();*/
        }
    }


    /*protected void checkForUpdates(boolean force) {
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

    }





    private void switchFromRegistration() {
        String firstName = registrationForm.getAuthFirstName();
        String lastName = registrationForm.getLastNameText();
        //Отсекаем только очевидный ляп.
        //С остальным пусть сервер разбирается
        if ((firstName == null || firstName.isEmpty())
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
                if (e.isCodeExpired()) {
                    //showCodeExpired();
                    return;
                }
                throw e;
            }
        } catch (Exception e) {
            //catchException(e);
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
        String firstName = registrationForm.getAuthFirstName();
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
        String firstName = registrationForm.getAuthFirstName();
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

    //--------------------------





    private void showPhoneNumberRequest(boolean clear) {
        setContentPanel(phoneForm);
        if (clear)
            phoneForm.clear();
        phoneForm.transferFocusTo();
    }



    private void sendAndRequestCode() throws IOException, ApiException {
        sendCode();
        showCodeRequest();
    }

    private void sendCode() throws IOException, ApiException {
        telegramDAO.sendCode();
    }

    private void showCodeRequest() {
        setContentPanel(codeForm);
        codeForm.clear();
        codeForm.transferFocusTo();
    }

    private void showNameInvalid() {
        showWarningMessage("Неверные регистрационные данные", "Внимание!");
        showNameRequest(false);
    }

    private void showNameRequest(boolean clear) {
        setContentPanel(registrationForm);
        if (clear)
            registrationForm.clear();
    }



    private void switchToMainScreen() throws ApiException {
        setContentPanel(mainForm);
        createTelegramProxy();
    }

    private void switchToBegin() {
        try {
            destroyTelegramProxy();
            setOverlayPanel(null);
            showPhoneNumberRequest(true);
            telegramDAO.logOut();
        } catch (Exception e) {
            //catchException(e);
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
            if (contains(person.getAuthFirstName().toLowerCase(), words)
                    || contains(person.getAuthLastName().toLowerCase(), words)) {
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



    private boolean tryAddContact(ContactInfo info) {

        String phone = info.getClearedPhone();
        if (phone.isEmpty()) {
            showWarningMessage("Пожалуйста, введите номер телефона", "Ошибка");
            return false;
        }
        if (info.getAuthFirstName().isEmpty() && info.getAuthLastName().isEmpty()) {
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
            telegramProxy.importContact(info.getPhone(), info.getAuthFirstName(), info.getAuthLastName());
        } catch (Exception e) {
            showWarningMessage("Ошибка на сервере при добавлении контакта", "Ошибка");
            return false;
        }

        setOverlayPanel(null);
        checkForUpdates(true);
        return true;
    }

    private boolean tryUpdateContact(ContactInfo info) {

        String phone = info.getClearedPhone();

        if (info.getAuthFirstName().isEmpty() && info.getAuthLastName().isEmpty()) {
            showWarningMessage("Пожалуйста, введите имя и/или фамилию", "Ошибка");
            return false;
        }

        try {
            telegramProxy.importContact(info.getPhone(), info.getAuthFirstName(), info.getAuthLastName());
        } catch (Exception e) {
            showWarningMessage("Ошибка на сервере при изменении контакта", "Ошибка");
            return false;
        }

        setOverlayPanel(null);
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

        setOverlayPanel(null);
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
*/

}
