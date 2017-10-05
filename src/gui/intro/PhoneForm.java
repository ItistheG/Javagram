package gui.intro;

import components.BlueButton;
import components.ImagePanel;
import gui.Helper;
import gui.resources.Images;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 * Created by HerrSergio on 05.04.2016.
 */
public class PhoneForm extends LetterBackground {
    private JPanel rootPanel;
    private JPanel iconPanel;
    private JTextPane hintTextPane;
    private JFormattedTextField phoneTextField;
    private JPanel phonePanel;
    private JButton okButton;
    private JPanel phoneIcon;

    public PhoneForm() {

        Helper.adjustTextPane(hintTextPane);

        Helper.clearBoth(phoneTextField);


        try {
            MaskFormatter maskFormatter = new MaskFormatter("+7 (###) ###-##-##");
            maskFormatter.setPlaceholder(null);
            maskFormatter.setPlaceholderCharacter('.');
            phoneTextField.setFormatterFactory(new DefaultFormatterFactory(maskFormatter));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.iconPanel.setBorder(BorderFactory.createEmptyBorder());

        //Альтернативное решение
        //BlueButton.decorateButton(okButton);
    }

    public String getPhoneNumber() {
        try {
            phoneTextField.commitEdit();
            return phoneTextField.getValue().toString();
        } catch (ParseException|NullPointerException e) {
            return null;
        }
    }

    public void addActionListenerForConfirm(ActionListener actionListener) {
        okButton.addActionListener(actionListener);
        phoneTextField.addActionListener(actionListener);
    }

    public void removeActionListenerForConfirm(ActionListener actionListener) {
        okButton.removeActionListener(actionListener);
        phoneTextField.removeActionListener(actionListener);
    }

    public void transferFocusTo() {
        phoneTextField.requestFocusInWindow();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

        iconPanel = new ImagePanel(Images.getLogo(), false, true, 0);

        phoneIcon = new ImagePanel(Images.getPhoneIcon(), false, true, 0);

        okButton = new BlueButton();
    }

    public void clear() {
        phoneTextField.setText("");
        phoneTextField.setValue("");
    }
}
