package gui.intro;

import components.BlueButton;
import components.ImagePanel;
import components.MaxLengthDocumentFilter;
import gui.Helper;
import gui.resources.Images;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.event.ActionListener;

/**
 * Created by HerrSergio on 06.04.2016.
 */
public class CodeForm extends LetterBackground {
    private JPanel iconPanel;
    private JLabel phoneLabel;
    private JTextPane hintTextPane;
    private JPanel rootPanel;
    private JPanel codePanel;
    private JPasswordField codePasswordField;
    private JButton okButton;
    private JPanel codeIcon;

    public CodeForm() {

        Helper.adjustTextPane(hintTextPane);

        Helper.clearBoth(codePasswordField);

        if(codePasswordField.getDocument() instanceof AbstractDocument)
            ((AbstractDocument) codePasswordField.getDocument()).setDocumentFilter(new MaxLengthDocumentFilter(5));

        this.iconPanel.setBorder(BorderFactory.createEmptyBorder());

        //Альтернативное решение
        //BlueButton.decorateButton(okButton);
    }

    public void setPhoneLabelText(String text) {
        phoneLabel.setText(text);
    }

    public String getPhoneLabelText() {
        return phoneLabel.getText();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

        iconPanel = new ImagePanel(Images.getLogo(), false, true, 0);

        codeIcon = new ImagePanel(Images.getLockIcon(), false, true, 0);

        okButton = new BlueButton();
    }

    public void addActionListenerForConfirm(ActionListener actionListener) {
        okButton.addActionListener(actionListener);
        codePasswordField.addActionListener(actionListener);
    }

    public void removeActionListenerForConfirm(ActionListener actionListener) {
        okButton.removeActionListener(actionListener);
        codePasswordField.removeActionListener(actionListener);
    }

    public void transferFocusTo() {
        codePasswordField.requestFocusInWindow();
    }

    public String getCode() {
        return new String(this.codePasswordField.getPassword());
    }

    public void clear() {
        codePasswordField.setText("");
    }
}
