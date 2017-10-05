package mvp.views.abs.intro;

import mvp.views.abs.ContentView;
import mvp.views.abs.IntroView;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public interface VerificationCodeView extends ContentView, IntroView {
    void transferFocusTo();
    String getCode();
    void clear();
    void setPhoneLabelText(String text);
    String getPhoneLabelText();
}
