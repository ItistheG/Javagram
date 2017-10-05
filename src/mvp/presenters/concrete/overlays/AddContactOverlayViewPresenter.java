package mvp.presenters.concrete.overlays;

import gui.overlays.ContactInfo;
import mvp.controller.abs.Controller;
import mvp.model.abs.TelegramModel;
import mvp.presenters.abs.overlays.AddContactPresenter;
import mvp.presenters.concrete.PresenterHelper;
import mvp.views.abs.overlays.AddContactOverlayView;
import mvp.views.abs.overlays.AddContactOverlayViewListener;
import mvp.views.abs.root.RootView;
import org.javagram.dao.Contact;
import org.javagram.dao.KnownPerson;
import org.javagram.dao.Person;

/**
 * Created by HerrSergio on 07.09.2017.
 */
public class AddContactOverlayViewPresenter implements AddContactPresenter, AddContactOverlayViewListener {
    private AddContactOverlayView view;
    private Controller controller;
    private TelegramModel model;

    public AddContactOverlayViewPresenter(Controller controller) {
        this.controller = controller;
        this.model = controller.getModel();
        this.view = controller.getAddContactOverlayView();
        view.setListener(this);
    }

    @Override
    public void start() {
        controller.getRootView().setOverlayView(view);
        displayContactInfo();
    }

    private void displayContactInfo() {
        ContactInfo contactInfo = new ContactInfo();
        Person person = PresenterHelper.getSelectedPerson(model);
        if (person instanceof KnownPerson && !(person instanceof Contact))
            contactInfo.setPhone(((KnownPerson) person).getPhoneNumber());
        view.setContactInfo(contactInfo);
    }

    @Override
    public void addContact() {
        ContactInfo info = view.getContactInfo();
        String phone = info.getClearedPhone();
        RootView rootView = controller.getRootView();
        if (phone.isEmpty()) {
            rootView.showWarningMessage("Пожалуйста, введите номер телефона", "Ошибка");
            return;
        }
        if (info.getFirstName().isEmpty() && info.getLastName().isEmpty()) {
            rootView.showWarningMessage("Пожалуйста, введите имя и/или фамилию", "Ошибка");
            return;
        }
        for (Person person : model.getPersons()) {
            if (person instanceof Contact) {
                if (((Contact) person).getPhoneNumber().replaceAll("\\D+", "").equals(phone)) {
                    rootView.showWarningMessage("Контакт с таким номером уже существует", "Ошибка");
                    return;
                }
            }
        }
        try {
            model.importContact(info.getPhone(), info.getFirstName(), info.getLastName());
        } catch (Exception e) {
            rootView.showWarningMessage("Ошибка на сервере при добавлении контакта", "Ошибка");
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
