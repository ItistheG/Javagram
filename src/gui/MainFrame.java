package gui;

import entities.TLSelfUser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by HerrSergio on 05.04.2016.
 */
public class MainFrame extends JFrame {

    private TLSelfUser selfUser;

    private PhoneForm phoneForm = new PhoneForm();
    private  CodeForm codeForm = new CodeForm();
    private  MainForm mainForm = new MainForm();

    private BufferedImage mainImage;
    private BufferedImage iconImage;

    {
        setTitle("Javagram");
        setContentPane(phoneForm.getRootPanel());
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 450));
        setLocationRelativeTo(null);

        phoneForm.runOnNextEvent(new Runnable() {
            @Override
            public void run() {
                String phoneNumber = phoneForm.getPhoneNumber();
                if(phoneNumber.isEmpty())
                    JOptionPane.showMessageDialog(MainFrame.this, "Введите корректный номер телефона!",  "Ошибка!", JOptionPane.ERROR_MESSAGE);
                else
                    switchFromPhoneToCode(phoneNumber);
            }
        });

        codeForm.runOnNextEvent(new Runnable() {
            @Override
            public void run() {
                String authCode = codeForm.getCode();
                switchFromCodeToMain(authCode);
            }
        });

        try {
            mainImage = ImageIO.read(this.getClass().getResourceAsStream("/images/Writing_a_letter.jpg"));
            phoneForm.setMainImage(mainImage);
            codeForm.setMainImage(mainImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            iconImage = ImageIO.read(this.getClass().getResource("/images/pre_mail.png"));
            phoneForm.setIconImage(iconImage);
            codeForm.setIconImage(iconImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void switchFromPhoneToCode(String phoneNumber) {

        try {
            //selfUser = new TLSelfUser(phoneNumber);
            //selfUser.sendCode();
            if(selfUser != null)
                throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MainFrame.this, "Критическая ошибка ApiBridge!",  "Ошибка!", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }


        this.changeContentPane(codeForm.getRootPanel());
        codeForm.getPhoneLabel().setText(phoneNumber);
        //JOptionPane.showMessageDialog(MainFrame.this, "Код отправлен",  "Информация", JOptionPane.INFORMATION_MESSAGE);

    }

    private void switchFromCodeToMain(String code) {
        changeContentPane(mainForm.getRooPanel());
    }

    private void changeContentPane(Container contentPane) {
        this.setContentPane(contentPane);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }
}
