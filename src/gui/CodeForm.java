package gui;

import components.GuiHelper;
import components.ImagePanel;
import components.MaxLengthDocumentFilter;
import resources.Images;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.event.ActionListener;

/**
 * Created by HerrSergio on 06.04.2016.
 */
public class CodeForm extends ImagePanel {
    private JPanel iconPanel;
    private JLabel phoneLabel;
    private JTextPane hintTextPane;
    private JPanel rootPanel;
    private JPanel phonePanel;
    private JPasswordField codePasswordField;
    private JButton okButton;

    public CodeForm() {
        super(Images.getBackground(), true, false, 0);

        GuiHelper.adjustTextPane(hintTextPane);

        codePasswordField.setBorder(BorderFactory.createEmptyBorder());
        if(codePasswordField.getDocument() instanceof AbstractDocument)
            ((AbstractDocument) codePasswordField.getDocument()).setDocumentFilter(new MaxLengthDocumentFilter(5));

        this.okButton.setContentAreaFilled(false);
        this.okButton.setOpaque(true);

        this.iconPanel.setBorder(BorderFactory.createEmptyBorder());
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
    }

    public void addActionListenerForConfirm(ActionListener actionListener) {
        okButton.addActionListener(actionListener);
        codePasswordField.addActionListener(actionListener);
    }

    public void removeActionListenerForConfirm(ActionListener actionListener) {
        okButton.removeActionListener(actionListener);
        codePasswordField.removeActionListener(actionListener);
    }

    public String getCode() {
        return new String(this.codePasswordField.getPassword());
    }

    public void clear() {
        codePasswordField.setText("");
        phoneLabel.setText("");
    }
}
