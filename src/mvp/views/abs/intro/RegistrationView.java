package mvp.views.abs.intro;

import mvp.views.abs.ContentView;
import mvp.views.abs.IntroView;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public interface RegistrationView extends ContentView,IntroView {
    void clear();
    String getFirstName();
    String getLastNameText();
}
