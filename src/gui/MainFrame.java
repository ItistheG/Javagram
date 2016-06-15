package gui;

import contacts.ContactsList;
import messsages.MessagesForm;
import misc.GuiHelper;
import org.javagram.dao.*;
import org.javagram.dao.Dialog;
import org.javagram.dao.proxy.TelegramProxy;
import org.javagram.dao.proxy.changes.UpdateChanges;
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
import java.io.IOException;


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

        phoneForm.setMainImage(Images.getBackground());
        codeForm.setMainImage(Images.getBackground());

        phoneForm.setIconImage(Images.getLogo());
        codeForm.setIconImage(Images.getLogo());

        mainForm.setContactsPanel(contactsList);

        contactsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {

                if(messagesFrozen != 0)
                    return;

                if(telegramProxy == null)  {
                    displayDialog(null);
                    return;
                }

                displayDialog(contactsList.getSelectedValue());
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

            if (updateChanges.getListChanged()) {
                updateContacts();
            } else if (updateChanges.getLargePhotosChanged().size() +
                    updateChanges.getSmallPhotosChanged().size() +
                    updateChanges.getStatusesChanged().size() != 0) {
                contactsList.repaint();
            } else {

            }

            Person currentBuddy = getMessagesForm().getPerson();
            Person targetPerson = contactsList.getSelectedValue();

            Dialog currentDialog = currentBuddy != null ? telegramProxy.getDialog(currentBuddy) : null;

            if (!GuiHelper.equal(targetPerson, currentBuddy) ||
                    updateChanges.getDialogsToReset().contains(currentDialog) ||
                    //updateChanges.getDialogsChanged().getChanged().containsKey(currentDialog) ||
                    updateChanges.getDialogsChanged().getDeleted().contains(currentDialog)) {
                updateMessages();
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
            changeContentPanel(mainForm);
            createTelegramProxy();
        } catch (Exception e) {
            showWarningMessage("Неверный код", "Внимание!");
        }
    }


    private void switchToBegin() {
        try {
            telegramDAO.logOut();
            telegramProxy = null;
        } catch (Exception e) {
            showErrorMessage("Продолжение работы не возможно", "Критическая ошибка!");
            abort(e);
        }
        changeContentPanel(phoneForm);
    }

    private void changeContentPanel(Container contentPanel) {
        undecoratedFrame.setContentPanel(contentPanel);
    }

    private void createTelegramProxy() {
        telegramProxy = new TelegramProxy(telegramDAO);

        messagesFrozen++;
        try {
            contactsList.setTelegramProxy(telegramProxy);
            contactsList.setSelectedValue(null);
            createMessagesForm();
            displayDialog(null);
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
            mainForm.setText(person != null ? person.getFirstName() + " " + person.getLastName() : null);
            revalidate();
            repaint();
        } catch (Exception e) {
            showErrorMessage("Проблема соединения с сервером", "проблемы в сети");
        }
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
