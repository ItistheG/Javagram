package components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by HerrSergio on 27.06.2016.
 */
public class ImageButton extends JButton {

    private BufferedImage image;

    {
        setOpaque(false);
    }

    public ImageButton(BufferedImage image) {
        this.image = image;
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
            if(isEnabled())
                GuiHelper.drawImage(graphics, image, 0, 0, this.getWidth(), this.getHeight());
        }
    }

    @Override
    protected void paintBorder(Graphics graphics) {
        if(image == null) {
            super.paintBorder(graphics);
        }
    }
}
