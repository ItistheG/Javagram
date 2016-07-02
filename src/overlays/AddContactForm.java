package overlays;

import components.HintTextField;
import components.ImageButton;
import components.OverlayBackground;
import resources.Images;

import javax.swing.*;
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
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

        closeButton = new ImageButton(Images.getCloseOverlay());
        addButton = new ImageButton(Images.getAddContact());

        firstNameTextField = new HintTextField("", "Имя", false) {
            @Override
            protected void paintBorder(Graphics graphics) {
                //super.paintBorder(graphics);
            }
        };
        lastNameTextField = new HintTextField("", "Фамилия", false) {
            @Override
            protected void paintBorder(Graphics graphics) {
                //super.paintBorder(graphics);
            }
        };
        phoneTextField = new HintTextField("", "Телефон", false) {
            @Override
            protected void paintBorder(Graphics graphics) {
                //super.paintBorder(graphics);
            }
        };
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
