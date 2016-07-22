package components;

import resources.Images;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by HerrSergio on 22.07.2016.
 */
public class BlueButton extends ExtendedImageButton {
    public BlueButton(String text) {
        super(Images.getBlueButton());
        setText(text);
        setForeground(Color.black);
    }

    public BlueButton() {
        this("");
    }

    private static Dimension createSize() {
        return new Dimension(80, 30);
    }

    public static BlueButton createOkButton() {
        BlueButton blueButton = new BlueButton("Ok");
        Dimension size = createSize();
        blueButton.setMinimumSize(size);
        blueButton.setPreferredSize(size);
        blueButton.setMaximumSize(size);
        blueButton.setSize(size);
        return blueButton;
    }

    public static BlueButton[] createYesNoButtons() {
        BlueButton yesButton = createOkButton();
        yesButton.setText("Да");
        BlueButton noButton = createOkButton();
        noButton.setText("Нет");
        return new BlueButton[] {yesButton, noButton};
    }
}
