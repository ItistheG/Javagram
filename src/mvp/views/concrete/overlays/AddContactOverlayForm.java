package mvp.views.concrete.overlays;

import gui.overlays.AddContactForm;
import mvp.views.abs.main.MainViewListener;
import mvp.views.abs.overlays.AddContactOverlayView;
import mvp.views.abs.overlays.AddContactOverlayViewListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by HerrSergio on 07.09.2017.
 */
public class AddContactOverlayForm extends AddContactForm implements AddContactOverlayView {

    private AddContactOverlayViewListener listener;

    @Override
    public AddContactOverlayViewListener getListener() {
        return listener;
    }

    @Override
    public void setListener(AddContactOverlayViewListener listener) {
        this.listener = listener;
    }

    {
        addActionListenerForAdd(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listener != null)
                    listener.addContact();
            }
        });
        addActionListenerForClose(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listener != null)
                    listener.closeView();
            }
        });
    }
}

