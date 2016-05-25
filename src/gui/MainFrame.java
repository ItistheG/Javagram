package gui;

import org.javagram.dao.TelegramDAO;
import org.javagram.dao.proxy.TelegramProxy;
import resources.Images;
import undecorated.ComponentResizerAbstract;
import undecorated.UndecoratedFrame;

import javax.swing.*;
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

    private UndecoratedFrame undecoratedFrame = new UndecoratedFrame(this, ComponentResizerAbstract.KEEP_RATIO_CENTER);

    private TelegramDAO telegramDAO;
    private TelegramProxy telegramProxy;

    private PhoneForm phoneForm = new PhoneForm();
    private CodeForm codeForm = new CodeForm();
    private MainForm mainForm = new MainForm();

    {
        setTitle("Javagram");
        setContentPane(undecoratedFrame);
        changeContentPanel(phoneForm);
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 450));
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
                if(phoneNumber == null)
                    JOptionPane.showMessageDialog(MainFrame.this, "Введите корректный номер телефона!",  "Ошибка!", JOptionPane.ERROR_MESSAGE);
                else
                    switchFromPhoneToCode(phoneNumber);
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
    }

    public MainFrame(TelegramDAO telegramDAO) throws HeadlessException {
        this.telegramDAO = telegramDAO;
    }

    private void switchFromPhoneToCode(String phoneNumber) {

        try {
            telegramDAO.acceptNumber(phoneNumber.replaceAll("[\\D]+", ""));
        } catch (IOException e) {

            return;
        }

        if(telegramDAO.canSignIn()) {
            try {
                telegramDAO.sendCode();
                codeForm.setPhoneLabelText(phoneNumber);
                changeContentPanel(codeForm);
            } catch (Exception e) {
                abort(e);
            }
        } else if(telegramDAO.canSignUp()) {

            return;
        } else {
            abort(null);
        }

    }

    private void switchFromCodeToMain(String code) {
        try {
            telegramDAO.signIn(code);
            changeContentPanel(mainForm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeContentPanel(Container contentPanel) {
        undecoratedFrame.setContentPanel(contentPanel);
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
}
