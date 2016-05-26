package undecorated;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by HerrSergio on 25.05.2016.
 */

public class UndecoratedOptionPane extends JDialog {

    private UndecoratedOptionPane() {

    }

    public static Object showDialog(Frame frame, JOptionPane optionPane, String title) {
        return show(new JDialog(frame, true), frame, optionPane, title);
    }

    public static Object showDialog(Dialog dialog, JOptionPane optionPane, String title) {
        return show(new JDialog(dialog, true), dialog, optionPane, title);
    }

    public static int showDialogInt(Frame frame, JOptionPane optionPane, String title) {
        return (Integer)showDialog(frame, optionPane, title);
    }

    public static int showDialogInt(Dialog dialog, JOptionPane optionPane, String title) {
        return (Integer)showDialog(dialog, optionPane, title);
    }


    private static Object show(final JDialog dialog, Window window, JOptionPane optionPane, String title) {
        dialog.setTitle(title);
        dialog.setContentPane(optionPane);
        UndecoratedFrame undecoratedFrame = new UndecoratedFrame(dialog, -1);
        dialog.pack();
        dialog.setLocationRelativeTo(window);
        optionPane.setValue(JOptionPane.INITIAL_VALUE_PROPERTY);
        PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                dialog.setVisible(false);
            }
        };
        optionPane.addPropertyChangeListener("value", propertyChangeListener);
        dialog.setVisible(true);
        optionPane.removePropertyChangeListener("value", propertyChangeListener);
        return optionPane.getValue();
    }

}
