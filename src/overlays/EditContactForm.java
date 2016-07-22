package overlays;

import components.*;
import resources.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by HerrSergio on 02.07.2016.
 */
public class EditContactForm extends OverlayBackground {
    private JButton closeButton;
    private JButton saveButton;
    private JPanel contactPanel;
    private JPanel rootPanel;
    private JLabel nameLabel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField phoneTextField;
    private JButton deleteButton;
    private JPanel photoPanel;

    private int id;

    {
        setContactInfo(new ContactInfo());
        setPhoto(null);

        ((HintTextField)firstNameTextField).setHintAlignment(JTextField.CENTER);
        ((HintTextField)lastNameTextField).setHintAlignment(JTextField.CENTER);
        ((HintTextField)phoneTextField).setHintAlignment(JTextField.CENTER);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

        closeButton = new ImageButton(Images.getCloseOverlay());
        deleteButton = new ImageButton(Images.getRemoveContact());
        saveButton = new ImageButton(Images.getUpdateContact());

        firstNameTextField = new HintTextFieldUnderlined("", "Имя", true, true);
        lastNameTextField = new HintTextFieldUnderlined("", "Фамилия", true, true);
        phoneTextField = new HintTextFieldUnderlined("", "Телефон", true, true);

        photoPanel = new ImagePanel(null, true, false, 0);
    }

    public void setContactInfo(ContactInfo info) {
        firstNameTextField.setText(info.getFirstName());
        lastNameTextField.setText(info.getLastName());
        phoneTextField.setText(info.getPhone());
        id = info.getId();
    }

    public ContactInfo getContactInfo() {
        return new ContactInfo(phoneTextField.getText().trim(),
                firstNameTextField.getText().trim(),
                lastNameTextField.getText().trim(),
                id);
    }

    public void setPhoto(BufferedImage photo) {
        ((ImagePanel)photoPanel).setImage(photo);
    }

    public BufferedImage getPhoto() {
        return ((ImagePanel)photoPanel).getImage();
    }

    public void addActionListenerForSave(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    public void removeActionListenerForSave(ActionListener actionListener) {
        saveButton.removeActionListener(actionListener);
    }

    public void addActionListenerForRemove(ActionListener actionListener) {
        deleteButton.addActionListener(actionListener);
    }

    public void removeActionListenerForRemove(ActionListener actionListener) {
        deleteButton.removeActionListener(actionListener);
    }

    public void addActionListenerForClose(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }

    public void removeActionListenerForClose(ActionListener actionListener) {
        closeButton.removeActionListener(actionListener);
    }
}
