package mvp.controller.concrete;

import mvp.controller.abs.AbstractController;
import mvp.model.abs.TelegramModel;
import mvp.presenters.abs.intro.PhoneNumberPresenter;
import mvp.presenters.abs.intro.RegistrationPresenter;
import mvp.presenters.abs.intro.VerificationCodePresenter;
import mvp.presenters.abs.overlays.AddContactPresenter;
import mvp.presenters.abs.overlays.EditDeleteContactPresenter;
import mvp.presenters.abs.overlays.ProfilePresenter;
import mvp.presenters.concrete.intro.*;
import mvp.presenters.abs.main.MainPresenter;
import mvp.presenters.concrete.main.MainViewPresenter;
import mvp.presenters.abs.root.RootPresenter;
import mvp.presenters.concrete.overlays.AddContactOverlayViewPresenter;
import mvp.presenters.concrete.overlays.EditDeleteContactOverlayViewPresenter;
import mvp.presenters.concrete.overlays.ProfileOverlayViewPresenter;
import mvp.presenters.concrete.root.RootViewPresenter;
import mvp.views.abs.intro.PhoneNumberView;
import mvp.views.abs.intro.RegistrationView;
import mvp.views.abs.intro.VerificationCodeView;
import mvp.views.abs.main.MainView;
import mvp.views.abs.overlays.AddContactOverlayView;
import mvp.views.abs.overlays.EditDeleteContactOverlayView;
import mvp.views.abs.overlays.ProfileOverlayView;
import mvp.views.abs.root.RootView;
import mvp.views.concrete.intro.PhoneNumberForm;
import mvp.views.concrete.intro.RegistrationForm;
import mvp.views.concrete.intro.VerificationCodeForm;
import mvp.views.concrete.main.MainForm;
import mvp.views.concrete.overlays.AddContactOverlayForm;
import mvp.views.concrete.overlays.EditDeleteContactOverlayForm;
import mvp.views.concrete.overlays.ProfileOverlayForm;
import mvp.views.concrete.root.RootFrameView;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public class ApplicationController extends AbstractController {

    public ApplicationController(TelegramModel model) {
        super(model);
    }

    @Override
    protected RootView createRootView() {
        return new RootFrameView();
    }

    @Override
    protected RootPresenter createRootPresenter() {
        return new RootViewPresenter(this);
    }

    @Override
    protected PhoneNumberView createPhoneNumberView() {
        return new PhoneNumberForm();
    }

    @Override
    protected PhoneNumberPresenter createPhoneNumberPresenter() {
        return new PhoneNumberViewPresenter(this);
    }

    @Override
    protected VerificationCodeView createVerificationCodeView() {
        return new VerificationCodeForm();
    }

    @Override
    protected VerificationCodePresenter createVerificationCodePresenter() {
        return new VerificationCodeViewPresenter(this);
    }

    @Override
    protected RegistrationView createRegistrationView() {
        return new RegistrationForm();
    }

    @Override
    protected RegistrationPresenter createRegistrationPresenter() {
        return new RegistrationViewPresenter(this);
    }

    @Override
    protected MainView createMainView() {
        return new MainForm();
    }

    @Override
    protected MainPresenter createMainPresenter() {
        return new MainViewPresenter(this);
    }


    @Override
    protected AddContactOverlayView createAddContactOverlayView() {
        return new AddContactOverlayForm();
    }

    @Override
    protected AddContactPresenter createAddContactPresenter() {
        return new AddContactOverlayViewPresenter(this);
    }

    @Override
    protected EditDeleteContactPresenter createEditDeleteContactPresenter() {
        return new EditDeleteContactOverlayViewPresenter(this);
    }

    @Override
    protected EditDeleteContactOverlayView createEditDeleteContactOverlayView() {
        return new EditDeleteContactOverlayForm();
    }

    @Override
    protected ProfilePresenter createProfilePresenter() {
        return new ProfileOverlayViewPresenter(this);
    }

    @Override
    protected ProfileOverlayView createProfileOverlayView() {
        return new ProfileOverlayForm();
    }
}
