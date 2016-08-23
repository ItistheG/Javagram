package mvp;

/**
 * Created by HerrSergio on 23.08.2016.
 */
public interface PhoneView extends View {
    void setPhonePresenter(PhonePresenter phonePresenter);
    PhonePresenter getPhonePresenter();
    String getPhoneNumber();
    void transferFocusTo();
}
