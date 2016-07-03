package overlays;

import components.*;
import resources.Images;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by HerrSergio on 02.07.2016.
 */
public class AddContactForm extends OverlayBackground {
    private JButton closeButton;
    private JButton addButton;
    private JPanel photoPanel;
    private JPanel rootPanel;
    private JLabel nameLabel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField phoneTextField;

    {
        setContactInfo(new ContactInfo());
        Document document = phoneTextField.getDocument();
        if(document instanceof AbstractDocument)
            ((AbstractDocument) document).setDocumentFilter(new PhoneNumberDocumentFilter());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

        closeButton = new ImageButton(Images.getCloseOverlay());
        addButton = new ImageButton(Images.getAddContact());

        firstNameTextField = new HintTextFieldUnderlined("", "Имя", false, true);
        lastNameTextField = new HintTextFieldUnderlined("", "Фамилия", false, true);
        phoneTextField = new HintTextFieldUnderlined("", "Телефон", false, true);
    }

    public void setContactInfo(ContactInfo info) {
        firstNameTextField.setText(info.getFirstName());
        lastNameTextField.setText(info.getLastName());
        phoneTextField.setText(info.getPhone());
    }

    public ContactInfo getContactInfo() {
        return new ContactInfo(phoneTextField.getText().trim(),
                firstNameTextField.getText().trim(),
                lastNameTextField.getText().trim());
    }

    public void addActionListenerForAdd(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }

    public void removeActionListenerForAdd(ActionListener actionListener) {
        addButton.removeActionListener(actionListener);
    }

    public void addActionListenerForClose(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }

    public void removeActionListenerForClose(ActionListener actionListener) {
        closeButton.removeActionListener(actionListener);
    }
}
