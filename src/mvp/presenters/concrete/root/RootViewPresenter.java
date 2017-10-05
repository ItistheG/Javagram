package mvp.presenters.concrete.root;

import mvp.controller.abs.Controller;
import mvp.presenters.abs.root.RootPresenter;
import mvp.views.abs.root.RootView;
import mvp.views.abs.root.RootViewListener;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public class RootViewPresenter implements RootPresenter, RootViewListener {

    private RootView view;
    private Controller controller;

    public RootViewPresenter(Controller controller) {
        this.view = controller.getRootView();
        view.setListener(this);
        this.controller = controller;
    }

    @Override
    public void start() {
        controller.getPhoneNumberPresenter().start();
        view.showView();
    }

    @Override
    public void onClosing() {
        if(view.showQuestionMessage("Уверены, что хотите выйти?", "Вопрос")) {
            view.hideView();
            controller.close();
        }
    }

    @Override
    public void onUpdate() {
        controller.getModel().update(false);
    }
}
