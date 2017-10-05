package mvp.views.abs.intro;

import mvp.views.abs.ContentView;
import mvp.views.abs.IntroView;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public interface PhoneNumberView extends ContentView, IntroView {
    String getPhoneNumber();
    void transferFocusTo();
    void clear();

}
