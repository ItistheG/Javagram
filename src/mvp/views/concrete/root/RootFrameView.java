package mvp.views.concrete.root;

import gui.frame.MainFrame;
import mvp.views.abs.ContentView;
import mvp.views.abs.OverlayView;
import mvp.views.abs.root.RootView;
import mvp.views.abs.root.RootViewListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public class RootFrameView extends MainFrame implements RootView {

    private RootViewListener listener;
    private Timer timer;

    {
        timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listener != null)
                    listener.onUpdate();
            }
        });
        timer.start();
    }

    @Override
    public RootViewListener getListener() {
        return listener;
    }

    @Override
    public void setListener(RootViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void setContentView(ContentView content) {
        setContentPanel((Component)content);
    }

    @Override
    public void setOverlayView(OverlayView overlay) {
        setOverlayPanel((Component) overlay);
    }

    @Override
    public ContentView getContentView() {
        return (ContentView) getContentPanel();
    }

    @Override
    public OverlayView getOverlayView() {
        return (OverlayView) getOverlayPanel();
    }

    @Override
    public void showErrorMessage(String text, String title) {
        super.showErrorMessage(text, title);
    }

    @Override
    public void showWarningMessage(String text, String title) {
        super.showWarningMessage(text, title);
    }

    @Override
    public void showInformationMessage(String text, String title) {
        super.showInformationMessage(text, title);
    }

    @Override
    public boolean showQuestionMessage(String text, String title) {
        return super.showQuestionMessage(text, title);
    }

    @Override
    protected void windowClosing(WindowEvent windowEvent) {
        super.windowClosing(windowEvent);
        if(listener != null)
            listener.onClosing();
    }
}
