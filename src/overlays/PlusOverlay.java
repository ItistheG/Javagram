package overlays;

import components.GuiHelper;
import resources.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by HerrSergio on 26.06.2016.
 */
public class PlusOverlay extends JPanel {
    private JButton plusButton;
    private JPanel rootPanel;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;

        plusButton = new JButton() {
            @Override
            protected void paintComponent(Graphics graphics) {
                //super.paintComponent(graphics);
                GuiHelper.drawImage(graphics, Images.getPlus(), 0, 0, this.getWidth(), this.getHeight());
            }

            @Override
            protected void paintBorder(Graphics graphics) {
                //super.paintBorder(graphics);
            }
        };
    }

    public void addActionListener(ActionListener actionListener) {
        plusButton.addActionListener(actionListener);
    }

    public void removeActionListener(ActionListener actionListener) {
        plusButton.removeActionListener(actionListener);
    }
}
