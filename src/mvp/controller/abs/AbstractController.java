package mvp.controller.abs;

import mvp.presenters.abs.overlays.AddContactPresenter;
import mvp.presenters.abs.overlays.EditDeleteContactPresenter;
import mvp.presenters.abs.overlays.ProfilePresenter;
import mvp.views.abs.overlays.AddContactOverlayView;
import mvp.views.abs.overlays.EditDeleteContactOverlayView;
import mvp.views.abs.overlays.ProfileOverlayView;
import org.javagram.dao.ApiException;
import mvp.model.abs.TelegramModel;
import mvp.presenters.abs.intro.PhoneNumberPresenter;
import mvp.presenters.abs.intro.RegistrationPresenter;
import mvp.presenters.abs.intro.VerificationCodePresenter;
import mvp.presenters.abs.main.MainPresenter;
import mvp.presenters.abs.root.RootPresenter;
import mvp.views.abs.intro.PhoneNumberView;
import mvp.views.abs.intro.RegistrationView;
import mvp.views.abs.intro.VerificationCodeView;
import mvp.views.abs.main.MainView;
import mvp.views.abs.root.RootView;

import java.io.IOException;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public abstract class AbstractController implements Controller {

    private RootView rootView;

    @Override
    public RootView getRootView() {
        if(rootView == null)
            rootView = createRootView();
        return rootView;
    }

    protected abstract RootView createRootView();

    private RootPresenter rootPresenter;

    @Override
    public RootPresenter getRootPresenter() {
        if(rootPresenter == null)
            rootPresenter = createRootPresenter();
        return rootPresenter;
    }

    protected abstract RootPresenter createRootPresenter();



    private PhoneNumberView phoneNumberView;

    @Override
    public PhoneNumberView getPhoneNumberView() {
        if(phoneNumberView == null)
            phoneNumberView = createPhoneNumberView();
        return phoneNumberView;
    }

    protected abstract PhoneNumberView createPhoneNumberView();

    private PhoneNumberPresenter phoneNumberPresenter;

    @Override
    public PhoneNumberPresenter getPhoneNumberPresenter() {
        if(phoneNumberPresenter == null)
            phoneNumberPresenter = createPhoneNumberPresenter();
        return phoneNumberPresenter;
    }

    protected abstract PhoneNumberPresenter createPhoneNumberPresenter();


    private VerificationCodeView verificationCodeView;

    @Override
    public VerificationCodeView getVerificationCodeView() {
        if(verificationCodeView == null)
            verificationCodeView = createVerificationCodeView();
        return verificationCodeView;
    }

    protected abstract VerificationCodeView createVerificationCodeView();

    private VerificationCodePresenter verificationCodePresenter;

    @Override
    public VerificationCodePresenter getVerificationCodePresenter() {
        if(verificationCodePresenter == null)
            verificationCodePresenter = createVerificationCodePresenter();
        return verificationCodePresenter;
    }

    protected abstract VerificationCodePresenter createVerificationCodePresenter();



    private RegistrationView registrationView;

    @Override
    public RegistrationView getRegistrationView() {
        if(registrationView == null)
            registrationView = createRegistrationView();
        return registrationView;
    }

    protected abstract RegistrationView createRegistrationView();

    private RegistrationPresenter registrationPresenter;

    @Override
    public RegistrationPresenter getRegistrationPresenter() {
        if(registrationPresenter == null)
            registrationPresenter = createRegistrationPresenter();
        return registrationPresenter;
    }

    protected abstract RegistrationPresenter createRegistrationPresenter();




    private MainView mainView;

    @Override
    public MainView getMainView() {
        if(mainView == null)
            mainView = createMainView();
        return mainView;
    }

    protected abstract MainView createMainView();

    private MainPresenter mainPresenter;

    @Override
    public MainPresenter getMainPresenter() {
        if(mainPresenter == null)
            mainPresenter = createMainPresenter();
        return mainPresenter;
    }

    protected abstract MainPresenter createMainPresenter();


    private AddContactOverlayView addContactOverlayView;

    @Override
    public AddContactOverlayView getAddContactOverlayView() {
        if(addContactOverlayView == null)
            addContactOverlayView = createAddContactOverlayView();
        return addContactOverlayView;
    }

    protected abstract AddContactOverlayView createAddContactOverlayView();


    private AddContactPresenter addContactPresenter;

    @Override
    public AddContactPresenter getAddContactPresenter() {
        if(addContactPresenter == null)
            addContactPresenter = createAddContactPresenter();
        return addContactPresenter;
    }

    protected abstract AddContactPresenter createAddContactPresenter();




    private EditDeleteContactPresenter editDeleteContactPresenter;

    @Override
    public EditDeleteContactPresenter getEditDeleteContactPresenter() {
        if(editDeleteContactPresenter == null)
            editDeleteContactPresenter = createEditDeleteContactPresenter();
        return editDeleteContactPresenter;
    }

    protected abstract EditDeleteContactPresenter createEditDeleteContactPresenter();


    private EditDeleteContactOverlayView editDeleteContactOverlayView;

    @Override
    public EditDeleteContactOverlayView getEditDeleteContactOverlayView() {
        if(editDeleteContactOverlayView == null)
            editDeleteContactOverlayView = createEditDeleteContactOverlayView();
        return editDeleteContactOverlayView;
    }

    protected abstract EditDeleteContactOverlayView createEditDeleteContactOverlayView();




    private ProfilePresenter profilePresenter;

    @Override
    public ProfilePresenter getProfilePresenter() {
        if(profilePresenter == null)
            profilePresenter = createProfilePresenter();
        return profilePresenter;
    }

    protected abstract ProfilePresenter createProfilePresenter();


    private ProfileOverlayView profileOverlayView;

    @Override
    public ProfileOverlayView getProfileOverlayView() {
        if(profileOverlayView == null)
            profileOverlayView = createProfileOverlayView();
        return profileOverlayView;
    }

    protected abstract ProfileOverlayView createProfileOverlayView();




    private TelegramModel model;

    public AbstractController(TelegramModel model) {
        this.model = model;
    }

    @Override
    public TelegramModel getModel() {
        return model;
    }

    @Override
    public void close() {
        try {
            model.close();
        } catch (IOException|ApiException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void abort(Throwable e) {
        if (e != null)
            e.printStackTrace();
        else
            System.err.println("Unknown Error");
        try {
            model.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.exit(-1);
    }

    public void catchException(Exception e) {
        RootView view = getRootView();
        if (e instanceof IOException) {
            view.showErrorMessage("Потеряно соединение с сервером", "Ошибка!");
        } else if (e instanceof ApiException) {
            view.showErrorMessage("Непредвиденная ошибка API :: " + e.getMessage(), "Ошибка!");
        } else {
            view.showErrorMessage("Непредвиденная ошибка", "Ошибка!");
        }
        abort(e);
    }
}
