package gui;

import contacts.ContactsList;
import org.javagram.dao.Person;
import org.javagram.dao.TelegramDAO;
import org.javagram.dao.proxy.TelegramProxy;
import org.javagram.dao.proxy.changes.UpdateChanges;
import resources.Images;
import undecorated.ComponentResizerAbstract;
import undecorated.UndecoratedFrame;
import undecorated.UndecoratedOptionPane;

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

    private UndecoratedFrame undecoratedFrame;

    private TelegramDAO telegramDAO;
    private TelegramProxy telegramProxy;

    private PhoneForm phoneForm = new PhoneForm();
    private CodeForm codeForm = new CodeForm();
    private MainForm mainForm = new MainForm();
    private ContactsList contactsList = new ContactsList();

    private Timer timer;

    {
        setTitle("Javagram");
        undecoratedFrame = new UndecoratedFrame(this, ComponentResizerAbstract.KEEP_RATIO_CENTER);

        changeContentPanel(phoneForm);
        setSize(925 + 4, 390 + 39);
        //setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        undecoratedFrame.addActionListenerForClose(e -> this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        undecoratedFrame.addActionListenerForMinimize(e -> this.setState(ICONIFIED));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                exit();
            }
        });

//-----------------------------ИЛИ--------------------------------------------//
//        undecoratedFrame.addActionListenerForClose(e -> dispose());
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        undecoratedFrame.addActionListenerForMinimize(e -> this.setState(ICONIFIED));
//
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosed(WindowEvent windowEvent) {
//                exit();
//            }
//        });

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

            }
        });

        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(telegramProxy != null) {
                    UpdateChanges updateChanges = telegramProxy.update();
                    if(updateChanges.getListChanged()) {
                        updateTelegramProxy();
                    } else if(updateChanges.getLargePhotosChanged().size() +
                            updateChanges.getSmallPhotosChanged().size() +
                            updateChanges.getStatusesChanged().size() != 0) {
                        contactsList.repaint();
                    } else {

                    }
                }
            }
        });
        timer.start();
    }

    public MainFrame(TelegramDAO telegramDAO) throws HeadlessException {
        this.telegramDAO = telegramDAO;
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
        updateTelegramProxy();
    }

    private void updateTelegramProxy() {
        Person person = contactsList.getSelectedValue();
        contactsList.setTelegramProxy(telegramProxy);
        contactsList.setSelectedValue(person);
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
        showDialog(new JOptionPane(text, JOptionPane.ERROR_MESSAGE), title);
    }

    private void showWarningMessage(String text, String title) {
        showDialog(new JOptionPane(text, JOptionPane.WARNING_MESSAGE), title);
    }


    private int showDialog(JOptionPane optionPane, String title) {
        return UndecoratedOptionPane.showDialogInt(this, optionPane, title);
    }
}
