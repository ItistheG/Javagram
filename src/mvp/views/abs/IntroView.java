package mvp.views.abs;

import mvp.views.abs.intro.ContinueListener;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public interface IntroView {
    void setListener(ContinueListener listener);
    ContinueListener getListener();
}
