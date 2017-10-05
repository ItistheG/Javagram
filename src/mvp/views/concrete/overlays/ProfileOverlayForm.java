package mvp.views.concrete.overlays;

import gui.overlays.ProfileForm;
import mvp.views.abs.overlays.EditDeleteContactOverlayViewListener;
import mvp.views.abs.overlays.ProfileOverlayView;
import mvp.views.abs.overlays.ProfileOverlayViewListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by HerrSergio on 07.09.2017.
 */
public class ProfileOverlayForm extends ProfileForm implements ProfileOverlayView {

    private ProfileOverlayViewListener listener;


    @Override
    public ProfileOverlayViewListener getListener() {
        return listener;
    }

    @Override
    public void setListener(ProfileOverlayViewListener listener) {
        this.listener = listener;
    }

    {
        addActionListenerForClose(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listener != null)
                    listener.closeView();
            }
        });

        addActionListenerForLogout(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listener != null)
                    listener.switchProfile();
            }
        });
    }
}
