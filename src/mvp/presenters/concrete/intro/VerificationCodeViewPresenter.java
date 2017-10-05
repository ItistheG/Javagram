package mvp.presenters.concrete.intro;

import mvp.controller.abs.Controller;
import mvp.model.abs.TelegramModel;
import org.javagram.dao.ApiException;
import mvp.presenters.abs.intro.VerificationCodePresenter;
import mvp.views.abs.intro.ContinueListener;
import mvp.views.abs.intro.VerificationCodeView;

import java.io.IOException;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public class VerificationCodeViewPresenter implements VerificationCodePresenter, ContinueListener {
    private VerificationCodeView view;
    private Controller controller;
    private TelegramModel model;

    public VerificationCodeViewPresenter(Controller controller) {
        this.controller = controller;
        view = controller.getVerificationCodeView();
        model = controller.getModel();
        view.setListener(this);
    }

    @Override
    public void start() {
        controller.getRootView().setContentView(view);
        view.setPhoneLabelText(model.getPhoneNumber());
        sendAndRequestCode();
    }

    public void sendAndRequestCode() {
        try {
            model.sendCode();
            showCodeRequest();
        } catch (Exception e) {
            controller.catchException(e);
        }
    }

    @Override
    public void continueAction() {
        String code = view.getCode();
        if (code == null || code.isEmpty()) {
            showCodeEmpty();
        } else {
            switchFromCode(code);
        }
    }

    private void showCodeInvalid() {
        controller.getRootView().showWarningMessage("Неверный код", "Внимание!");
        showCodeRequest();
    }

    private void showCodeRequest() {
        view.clear();
        view.transferFocusTo();
    }

    private void showCodeEmpty() {
        controller.getRootView().showWarningMessage("Не введен код", "Внимание!");
        showCodeRequest();
    }

    private void showCodeExpired() throws IOException, ApiException {
        controller.getRootView().showWarningMessage("Код устарел. Отправляю новый", "Внимание!");
        sendAndRequestCode();
    }

    private void switchFromCode(String code) {
        try {
            try {
                model.acceptAuthCode(code);
                model.signIn();
                switchToMainScreen();
            } catch (ApiException e) {
                if (e.isCodeInvalid()) {
                    showCodeInvalid();
                    return;
                }
                if (e.isCodeEmpty()) {
                    showCodeEmpty();
                    return;
                }
                if (e.isCodeExpired()) {
                    showCodeExpired();
                    return;
                }
                if (e.isPhoneNumberUnoccupied()) {
                    showNameRequest();
                    return;
                }
                throw e;
            }
        } catch (Exception e) {
            controller.catchException(e);
        }
    }

    private void showNameRequest() {
        controller.getRegistrationPresenter().start();
    }

    private void switchToMainScreen() {
        controller.getMainPresenter().start();
    }
}
