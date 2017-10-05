package mvp.presenters.concrete.intro;

import mvp.controller.abs.Controller;
import org.javagram.dao.ApiException;
import mvp.model.abs.TelegramModel;
import mvp.presenters.abs.intro.PhoneNumberPresenter;
import mvp.views.abs.intro.ContinueListener;
import mvp.views.abs.intro.PhoneNumberView;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public class PhoneNumberViewPresenter implements PhoneNumberPresenter, ContinueListener {
    private PhoneNumberView view;
    private Controller controller;
    private TelegramModel model;

    public PhoneNumberViewPresenter(Controller controller) {
        this.controller = controller;
        this.view = controller.getPhoneNumberView();
        view.setListener(this);
        model = controller.getModel();
    }

    @Override
    public void continueAction() {
        String phoneNumber = view.getPhoneNumber();
        if(phoneNumber == null) {
            showPhoneNumberEmpty();
        } else {
            switchFromPhone(phoneNumber);
        }
    }

    private void switchFromPhone(String phoneNumber) {

        try {
            try {
                model.acceptNumber(phoneNumber.replaceAll("[\\D]+", ""));

                if (model.canSignUp()) {
                    if (!controller.getRootView().showQuestionMessage("Пользователь не зарегистрирован. Будет регистрироваться?", "Внимание!")) {
                        signalNewNumber();
                        return;
                    }
                }

                ///mvp.model.sendCode();
                controller.getVerificationCodePresenter().start();

            } catch (ApiException e) {
                if (e.isPhoneNumberInvalid()) {
                    showPhoneNumberInvalid();
                    return;
                }
                throw e;
            }
        } catch (Exception e) {
            controller.catchException(e);
        }
    }

    private void showPhoneNumberEmpty() {
        controller.getRootView().showWarningMessage("Введите корректный номер телефона!", "Внимание!");
        signalNewNumber();
    }

    private void signalNewNumber() {
        view.clear();
        view.transferFocusTo();
    }

    private void showPhoneNumberInvalid() {
        controller.getRootView().showWarningMessage("Номер телефона введен не верно", "Внимание!");
        signalNewNumber();
    }

    @Override
    public void start() {
        controller.getRootView().setContentView(view);
        signalNewNumber();
    }
}
