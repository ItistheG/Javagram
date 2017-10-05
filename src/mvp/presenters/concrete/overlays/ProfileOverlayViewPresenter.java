package mvp.presenters.concrete.overlays;

import gui.overlays.ContactInfo;
import mvp.controller.abs.Controller;
import mvp.model.abs.TelegramModel;
import mvp.presenters.abs.overlays.ProfilePresenter;
import mvp.presenters.concrete.PresenterHelper;
import mvp.views.abs.overlays.EditDeleteContactOverlayView;
import mvp.views.abs.overlays.ProfileOverlayView;
import mvp.views.abs.overlays.ProfileOverlayViewListener;
import org.javagram.dao.Me;

/**
 * Created by HerrSergio on 07.09.2017.
 */
public class ProfileOverlayViewPresenter implements ProfilePresenter, ProfileOverlayViewListener {
    private ProfileOverlayView view;
    private Controller controller;
    private TelegramModel model;

    public ProfileOverlayViewPresenter(Controller controller) {
        this.controller = controller;
        this.model = controller.getModel();
        this.view = controller.getProfileOverlayView();
        view.setListener(this);
    }

    @Override
    public void switchProfile() {
        try {
            model.logOut();
            controller.getRootView().setOverlayView(null);
            controller.getPhoneNumberPresenter().start();
        } catch (Exception e) {
            controller.catchException(e);
        }
    }

    @Override
    public void closeView() {
        controller.getRootView().setOverlayView(null);
    }

    @Override
    public void start() {
        Me me = model.getMe();
        ContactInfo contactInfo = PresenterHelper.toContactInfo(me, model, false);
        view.setContactInfo(contactInfo);
        controller.getRootView().setOverlayView(view);
    }
}
