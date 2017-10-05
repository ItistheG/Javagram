package mvp.views.concrete.overlays;

import gui.overlays.EditContactForm;
import mvp.views.abs.overlays.EditDeleteContactOverlayView;
import mvp.views.abs.overlays.EditDeleteContactOverlayViewListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by HerrSergio on 07.09.2017.
 */
public class EditDeleteContactOverlayForm extends EditContactForm implements EditDeleteContactOverlayView {
    private EditDeleteContactOverlayViewListener listener;

    @Override
    public EditDeleteContactOverlayViewListener getListener() {
        return listener;
    }

    @Override
    public void setListener(EditDeleteContactOverlayViewListener listener) {
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

        addActionListenerForRemove(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listener != null)
                    listener.deleteContact();
            }
        });

        addActionListenerForSave(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listener != null)
                    listener.saveContact();
            }
        });
    }
}
