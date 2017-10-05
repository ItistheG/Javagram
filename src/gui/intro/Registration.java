package gui.intro;

import components.BlueButton;
import components.HintTextField;
import components.HintTextFieldUnderlined;
import gui.Helper;
import gui.resources.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by HerrSergio on 20.02.2017.
 */
public class Registration extends LetterBackground {
    private JPanel rootPanel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JLabel registrationLabel;
    private JButton okButton;

    {
        registrationLabel.setFont(Fonts.getNameFont().deriveFont(0, 45));
        registrationLabel.setForeground(Color.white);

        Helper.clearBoth(firstNameTextField);
        Helper.clearBoth(lastNameTextField);

        ((HintTextField)firstNameTextField).setHintAlignment(JTextField.CENTER);
        ((HintTextField)lastNameTextField).setHintAlignment(JTextField.CENTER);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

        firstNameTextField = new HintTextFieldUnderlined("", "Имя", true, true);
        lastNameTextField = new HintTextFieldUnderlined("", "Фамилия", true, true);

        okButton = new BlueButton();
    }

    public void clear() {
        firstNameTextField.setText("");
        lastNameTextField.setText("");
    }

    public void addActionListenerForConfirm(ActionListener actionListener) {
        okButton.addActionListener(actionListener);
    }

    public void removeActionListenerForConfirm(ActionListener actionListener) {
        okButton.removeActionListener(actionListener);
    }

    public String getFirstName() {
        return firstNameTextField.getText();
    }

    public String getLastNameText() {
        return lastNameTextField.getText();
    }
}
