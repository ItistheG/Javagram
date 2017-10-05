package mvp.views.abs.overlays;

import gui.overlays.ContactInfo;
import mvp.views.abs.OverlayView;

/**
 * Created by HerrSergio on 07.09.2017.
 */
public interface EditDeleteContactOverlayView extends OverlayView {
    void setListener(EditDeleteContactOverlayViewListener listener);
    EditDeleteContactOverlayViewListener getListener();

    void setContactInfo(ContactInfo info);
    ContactInfo getContactInfo();
}
