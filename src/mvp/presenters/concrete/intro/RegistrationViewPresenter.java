package mvp.presenters.concrete.intro;

import mvp.controller.abs.Controller;
import mvp.model.abs.TelegramModel;
import org.javagram.dao.ApiException;
import mvp.presenters.abs.intro.RegistrationPresenter;
import mvp.views.abs.intro.ContinueListener;
import mvp.views.abs.intro.RegistrationView;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public class RegistrationViewPresenter implements RegistrationPresenter, ContinueListener {
    private RegistrationView view;
    private Controller controller;
    private TelegramModel model;

    public RegistrationViewPresenter(Controller controller) {
        this.controller = controller;
        view = controller.getRegistrationView();
        model = controller.getModel();
        view.setListener(this);
    }

    @Override
    public void start() {
        controller.getRootView().setContentView(view);
        showNameRequest();
    }

    @Override
    public void continueAction() {
        String firstName = view.getFirstName();
        String lastName = view.getLastNameText();
        //Отсекаем только очевидный ляп.
        //С остальным пусть сервер разбирается
        if ((firstName == null || firstName.isEmpty())
                && (lastName == null || lastName.isEmpty())) {
            showNameInvalid();
        } else {
            switchFromRegistration(firstName, lastName);
        }
    }

    private void switchFromRegistration(String firstName, String lastName) {
        try {
            try {
                model.acceptName(firstName, lastName);
                model.signUp();
                switchToMainScreen();
            } catch (ApiException e) {
                if (e.isNameInvalid()) {
                    showNameInvalid();
                    return;
                }
                if (e.isCodeExpired()) {
                    showCodeExpired();
                    return;
                }
                throw e;
            }
        } catch (Exception e) {
            controller.catchException(e);
        }
    }

    private void showCodeExpired() {
        controller.getRootView().showWarningMessage("Код устарел. Отправляю новый", "Внимание!");
        controller.getVerificationCodePresenter().start();
    }

    private void showNameInvalid() {
        controller.getRootView().showWarningMessage("Неверные регистрационные данные", "Внимание!");
        showNameRequest();
    }

    private void showNameRequest() {
        view.clear();
    }

    private void switchToMainScreen() {
        controller.getMainPresenter().start();
    }
}
