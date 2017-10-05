package mvp.views.concrete.intro;

import gui.intro.CodeForm;
import mvp.views.abs.intro.ContinueListener;
import mvp.views.abs.intro.VerificationCodeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by HerrSergio on 17.08.2017.
 */
public class VerificationCodeForm extends CodeForm implements VerificationCodeView {

    {
        addActionListenerForConfirm(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(listener != null)
                    listener.continueAction();
            }
        });
    }

    private ContinueListener listener;

    @Override
    public ContinueListener getListener() {
        return listener;
    }

    @Override
    public void setListener(ContinueListener listener) {
        this.listener = listener;
    }

}
