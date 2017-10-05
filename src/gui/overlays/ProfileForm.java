package gui.overlays;

import components.GuiHelper;
import components.ImagePanel;
import components.OverlayBackground;
import gui.resources.Fonts;
import gui.resources.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by HerrSergio on 15.06.2016.
 */
public class ProfileForm extends OverlayBackground {
    private JButton closeButton;
    private JButton logoutButton;
    private JPanel photoPanel;
    private JPanel rootPanel;
    private JLabel nameLabel;
    private JLabel phoneLabel;

    private final static String phoneRegexFrom = "^\\+?(\\d*)(\\d{3})(\\d{3})(\\d{2})(\\d{2})$", phoneRegexTo = "+$1($2)$3-$4-$5";

    private String phone;
    private int id = 0;

    {
        nameLabel.setFont(Fonts.getNameFont().deriveFont(0, 45));
        nameLabel.setForeground(Color.white);

        phoneLabel.setFont(Fonts.getNameFont().deriveFont(0, 30));
        phoneLabel.setForeground(Color.white);

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
        ContactInfo info = new ContactInfo();
        String[] data = nameLabel.getText().trim().split("\\s+", 2); //На случай редактирования, которого пока нет
        info.setFirstName(data.length > 0 ? data[0] : "");
        info.setLastName(data.length > 1 ? data[1] : "");
        info.setPhone(phone);
        info.setPhoto((BufferedImage) ((ImagePanel)photoPanel).getImage());
        info.setId(id);
        return info;
    }

    public void setContactInfo(ContactInfo contactInfo) {

        if (contactInfo != null) {
            ((ImagePanel) photoPanel).setImage(contactInfo.getPhoto());
            nameLabel.setText(contactInfo.getFirstName() + " " + contactInfo.getLastName());
            phone = contactInfo.getPhone();
            phoneLabel.setText(contactInfo.getClearedPhone().replaceAll(phoneRegexFrom, phoneRegexTo));
            id = contactInfo.getId();
        } else {
            ((ImagePanel) photoPanel).setImage(null);
            nameLabel.setText("");
            phone = "";
            phoneLabel.setText("");
            id = 0;
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
