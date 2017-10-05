package mvp.views.abs.root;

import mvp.views.abs.ContentView;
import mvp.views.abs.OverlayView;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public interface RootView {

    void setContentView(ContentView content);
    void setOverlayView(OverlayView overlay);
    ContentView getContentView();
    OverlayView getOverlayView();

    void showErrorMessage(String text, String title);
    void showWarningMessage(String text, String title);
    void showInformationMessage(String text, String title);
    boolean showQuestionMessage(String text, String title);

    void showView();
    void hideView();

    void setListener(RootViewListener listener);
    RootViewListener getListener();
}
