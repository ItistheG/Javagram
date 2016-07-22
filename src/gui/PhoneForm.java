package gui;

import components.BlueButton;
import components.GuiHelper;
import components.ImagePanel;
import resources.Images;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.text.ParseException;

/**
 * Created by HerrSergio on 05.04.2016.
 */
public class PhoneForm extends ImagePanel {
    private JPanel rootPanel;
    private JPanel iconPanel;
    private JTextPane hintTextPane;
    private JFormattedTextField phoneTextField;
    private JPanel phonePanel;
    private JButton okButton;

    public PhoneForm() {

        super(Images.getBackground(), true, false, 0);

        GuiHelper.adjustTextPane(hintTextPane);

        phoneTextField.setBorder(BorderFactory.createEmptyBorder());

        try {
            MaskFormatter maskFormatter = new MaskFormatter("+7 (###) ###-##-##");
            maskFormatter.setPlaceholder(null);
            maskFormatter.setPlaceholderCharacter('.');
            phoneTextField.setFormatterFactory(new DefaultFormatterFactory(maskFormatter));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.iconPanel.setBorder(BorderFactory.createEmptyBorder());
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

        okButton = new BlueButton();
    }

    public void clear() {
        phoneTextField.setText("");
        phoneTextField.setValue("");
    }
}
