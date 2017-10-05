import mvp.controller.concrete.ApplicationController;
import mvp.controller.abs.Controller;
import mvp.model.abs.TelegramModel;
import mvp.model.concrete.ApiBridgeTelegramModel;
import mvp.model.concrete.DebugTelegramModel;
import mvp.presenters.abs.root.RootPresenter;

import javax.swing.*;

/**
 * Created by HerrSergio on 05.04.2016.
 */
public class Loader {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if(info.getName().equals("Nimbus")) {
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                    //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    TelegramModel telegramModel =  new ApiBridgeTelegramModel(Config.SERVER, Config.APP_ID, Config.APP_HASH);
                            //new DebugTelegramModel();
                    Controller controller = new ApplicationController(telegramModel);
                    RootPresenter presenter = controller.getRootPresenter();
                    presenter.start();
                    //frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }
}
