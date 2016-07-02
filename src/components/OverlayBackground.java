package components;

import javax.swing.*;
import java.awt.*;

/**
 * Created by HerrSergio on 02.07.2016.
 */
public class OverlayBackground extends JPanel {
    @Override
    protected void paintComponent(Graphics graphics) {
        //super.paintComponent(graphics);
        Color color = Color.black;
        graphics.setColor(GuiHelper.makeTransparent(color, 0.7f));
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
