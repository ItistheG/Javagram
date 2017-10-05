package mvp.controller.abs;

import mvp.model.abs.TelegramModel;
import mvp.presenters.abs.intro.PhoneNumberPresenter;
import mvp.presenters.abs.intro.RegistrationPresenter;
import mvp.presenters.abs.intro.VerificationCodePresenter;
import mvp.presenters.abs.main.MainPresenter;
import mvp.presenters.abs.overlays.AddContactPresenter;
import mvp.presenters.abs.overlays.EditDeleteContactPresenter;
import mvp.presenters.abs.overlays.ProfilePresenter;
import mvp.presenters.abs.root.RootPresenter;
import mvp.presenters.concrete.overlays.ProfileOverlayViewPresenter;
import mvp.views.abs.intro.PhoneNumberView;
import mvp.views.abs.intro.RegistrationView;
import mvp.views.abs.intro.VerificationCodeView;
import mvp.views.abs.main.MainView;
import mvp.views.abs.overlays.AddContactOverlayView;
import mvp.views.abs.overlays.EditDeleteContactOverlayView;
import mvp.views.abs.overlays.ProfileOverlayView;
import mvp.views.abs.overlays.ProfileOverlayViewListener;
import mvp.views.abs.root.RootView;

import java.io.Closeable;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public interface Controller extends Closeable {
    RootView getRootView();
    RootPresenter getRootPresenter();

    PhoneNumberView getPhoneNumberView();
    PhoneNumberPresenter getPhoneNumberPresenter();

    VerificationCodeView getVerificationCodeView();
    VerificationCodePresenter getVerificationCodePresenter();

    RegistrationView getRegistrationView();
    RegistrationPresenter getRegistrationPresenter();

    AddContactOverlayView getAddContactOverlayView();
    AddContactPresenter getAddContactPresenter();

    EditDeleteContactOverlayView getEditDeleteContactOverlayView();
    EditDeleteContactPresenter getEditDeleteContactPresenter();


    ProfileOverlayView getProfileOverlayView();
    ProfilePresenter getProfilePresenter();

    MainView getMainView();
    MainPresenter getMainPresenter();

    TelegramModel getModel();
    void close();
    void abort(Throwable e);
    void catchException(Exception e);
}
