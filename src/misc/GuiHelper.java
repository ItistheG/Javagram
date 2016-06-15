package misc;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by HerrSergio on 14.06.2016.
 */
public class GuiHelper {

    private GuiHelper() {

    }

    public static void decorateScrollPane(JScrollPane scrollPane) {
        int width = 3;

        JScrollBar verticalScrollBar =  scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new MyScrollbarUI());
        verticalScrollBar.setPreferredSize(new Dimension(width, Integer.MAX_VALUE));

        JScrollBar horizontalScrollBar =  scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setUI(new MyScrollbarUI());
        horizontalScrollBar.setPreferredSize(new Dimension(Integer.MAX_VALUE, width));

        for (String corner : new String[] {ScrollPaneConstants.LOWER_RIGHT_CORNER, ScrollPaneConstants.LOWER_LEFT_CORNER,
                ScrollPaneConstants.UPPER_LEFT_CORNER, ScrollPaneConstants.UPPER_RIGHT_CORNER}) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.white);
            scrollPane.setCorner(corner, panel);
        }
    }

    public static void drawImage(Graphics g, BufferedImage image, int x, int y, int width, int height) {
        double dx = width * 1.0 / image.getWidth();
        double dy = height * 1.0 / image.getHeight();
        double d = Math.min(dy, dx);
        int imageWidth = (int)Math.round(d * image.getWidth());
        int imageHeight = (int)Math.round(d * image.getHeight());
        x += (width - imageWidth) / 2;
        y += (height - imageHeight) / 2;
        g.drawImage(image, x, y, imageWidth, imageHeight, null);
    }

    public static boolean equal(Object a, Object b) {
        return a == b || a != null && a.equals(b);
    }

    public static Color makeTransparent(Color color,float transparency) {
        if(transparency < 0.0f || transparency > 1.0f)
            throw new IllegalArgumentException();
        return new Color(color.getRed(),color.getGreen(), color.getBlue(), (int)Math.round(color.getAlpha() * transparency));
    }
}
