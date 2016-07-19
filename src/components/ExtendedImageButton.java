package components;

import resources.Images;

import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Created by HerrSergio on 19.07.2016.
 */
public class ExtendedImageButton extends ImageButton {

    private int inset = 5;

    public ExtendedImageButton(BufferedImage image) {
        super(image, false);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if(isEnabled()) {

            int width = getWidth() - 2 * inset;

            String text = getText();

            FontMetrics fontMetrics = graphics.getFontMetrics();

            while (fontMetrics.stringWidth(text) > width) {
                int len = text.length() - 4;
                if (len >= 0) {
                    text = text.substring(0, len) + "...";
                } else if (text.isEmpty()) {
                    break;
                } else {
                    len = text.length() - 1;
                    text = text.substring(0, len);
                }
            }

            int textWidth = fontMetrics.stringWidth(text);

            int x = inset + (width - textWidth) / 2;
            int y = getBaseline(getWidth(), getHeight());

            if (!text.isEmpty()) {
                graphics.drawString(text, x, y);
            }

            if (isFocusOwner() && isFocusPainted()) {
                int y0 = y + fontMetrics.getDescent() / 2;
                int x1 = x - inset;
                int x2 = x + textWidth + inset;
                graphics.drawLine(x1, y0, x2, y0);
            }
        }
    }
}
