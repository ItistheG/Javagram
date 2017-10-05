package mvp.presenters.concrete.overlays;

import gui.overlays.ContactInfo;
import gui.overlays.EditContactForm;
import mvp.controller.abs.Controller;
import mvp.model.abs.TelegramModel;
import mvp.presenters.abs.overlays.EditDeleteContactPresenter;
import mvp.presenters.concrete.PresenterHelper;
import mvp.views.abs.overlays.EditDeleteContactOverlayView;
import mvp.views.abs.overlays.EditDeleteContactOverlayViewListener;
import mvp.views.abs.root.RootView;
import org.javagram.dao.Contact;
import org.javagram.dao.Person;

/**
 * Created by HerrSergio on 07.09.2017.
 */
public class EditDeleteContactOverlayViewPresenter implements EditDeleteContactPresenter, EditDeleteContactOverlayViewListener {

    private EditDeleteContactOverlayView view;
    private Controller controller;
    private TelegramModel model;

    public EditDeleteContactOverlayViewPresenter(Controller controller) {
        this.controller = controller;
        this.model = controller.getModel();
        this.view = controller.getEditDeleteContactOverlayView();
        view.setListener(this);
    }

    @Override
    public void start() {
        Person person = PresenterHelper.getSelectedPerson(model);
        if (person instanceof Contact) {
            view.setContactInfo(PresenterHelper.toContactInfo((Contact) person, model, false));
            controller.getRootView().setOverlayView(view);
        }
    }

    @Override
    public void saveContact() {
        ContactInfo info = view.getContactInfo();
        //String phone = info.getClearedPhone();
        RootView rootView = controller.getRootView();

        if (info.getFirstName().isEmpty() && info.getLastName().isEmpty()) {
            rootView.showWarningMessage("Пожалуйста, введите имя и/или фамилию", "Ошибка");
            return;
        }

        try {
            model.importContact(info.getPhone(), info.getFirstName(), info.getLastName());
        } catch (Exception e) {
            rootView.showWarningMessage("Ошибка на сервере при изменении контакта", "Ошибка");
            return;
        }

        rootView.setOverlayView(null);
        model.update(true);
    }

    @Override
    public void deleteContact() {
        ContactInfo info = view.getContactInfo();
        int id = info.getId();
        RootView rootView = controller.getRootView();

        try {
            model.deleteContact(id);
        } catch (Exception e) {
            rootView.showWarningMessage("Ошибка на сервере при удалении контакта", "Ошибка");
            return;
        }

        rootView.setOverlayView(null);
        model.update(true);
    }

    @Override
    public void closeView() {
        controller.getRootView().setOverlayView(null);
    }
}
