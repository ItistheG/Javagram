package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by HerrSergio on 25.05.2016.
 */
public class MyScrollBarCorner extends JPanel {

    private Color color;

    {
        setBorder(BorderFactory.createEmptyBorder());
    }

    public MyScrollBarCorner(Color color) {
        this.color = color;
        setOpaque(color.getAlpha() == Byte.MAX_VALUE);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
    }

}
