package components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by HerrSergio on 27.06.2016.
 */
public class ImageButton extends JButton {

    private BufferedImage image;
    private boolean keepRatio;

    {
        setOpaque(false);
    }

    public ImageButton(BufferedImage image) {
        this(image, true);
    }

    public ImageButton(BufferedImage image, boolean keepRatio) {
        this.image = image;
        this.keepRatio = keepRatio;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        if(this.image != image) {
            this.image = image;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        if(image == null) {
            super.paintComponent(graphics);
        } else  {
            if(isOpaque()) {
                graphics.setColor(getBackground());
                graphics.fillRect(0, 0, getWidth(), getHeight());
            }
            if(isEnabled()) {
                if(keepRatio)
                    GuiHelper.drawImage(graphics, image, 0, 0, this.getWidth(), this.getHeight());
                else
                    graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        }
    }

    @Override
    protected void paintBorder(Graphics graphics) {
        if(image == null) {
            super.paintBorder(graphics);
        }
    }
}
