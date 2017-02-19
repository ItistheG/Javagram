package gui.overlays;

import components.GuiHelper;
import components.ImagePanel;
import components.OverlayBackground;
import gui.resources.Fonts;
import gui.resources.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by HerrSergio on 15.06.2016.
 */
public class ProfileForm extends OverlayBackground {
    private JButton closeButton;
    private JButton logoutButton;
    private JPanel photoPanel;
    private JPanel rootPanel;
    private JLabel nameLabel;

    private ContactInfo contactInfo;

    {
        nameLabel.setFont(Fonts.getNameFont().deriveFont(0, 45));
        nameLabel.setForeground(Color.white);

        GuiHelper.decorateAsImageButton(closeButton, Images.getCloseOverlay());
        GuiHelper.decorateAsImageButton(logoutButton, Images.getLogoutIcon());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

        photoPanel = new ImagePanel(null, true, true, 0);

        //Альтернтивное решение
        //closeButton = new ImageButton(Images.getCloseOverlay());
        //logoutButton = new ImageButton(Images.getLogoutIcon());
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        if(this.contactInfo != contactInfo) {
            this.contactInfo = contactInfo;
            if(contactInfo != null){
                ((ImagePanel)photoPanel).setImage(contactInfo.getPhoto());
                nameLabel.setText(contactInfo.getFirstName() + " " + contactInfo.getLastName());
            } else {
                ((ImagePanel)photoPanel).setImage(null);
                nameLabel.setText("");
            }
        }
    }

    public void addActionListenerForLogout(ActionListener actionListener) {
        logoutButton.addActionListener(actionListener);
    }

    public void removeActionListenerForLogout(ActionListener actionListener) {
        logoutButton.removeActionListener(actionListener);
    }

    public void addActionListenerForClose(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }

    public void removeActionListenerForClose(ActionListener actionListener) {
        closeButton.removeActionListener(actionListener);
    }
}
