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
public class EditContactForm extends OverlayBackground {
    private JButton closeButton;
    private JButton saveButton;
    private JPanel photoPanel;
    private JPanel rootPanel;
    private JLabel nameLabel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField phoneTextField;
    private JButton deleteButton;

    private int id;

    {
        setContactInfo(new ContactInfo());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

        closeButton = new ImageButton(Images.getCloseOverlay());
        deleteButton = new ImageButton(Images.getRemoveContact());
        saveButton = new ImageButton(Images.getUpdateContact());

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
        id = info.getId();
    }

    public ContactInfo getContactInfo() {
        return new ContactInfo(phoneTextField.getText().trim(),
                firstNameTextField.getText().trim(),
                lastNameTextField.getText().trim(),
                id);
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
