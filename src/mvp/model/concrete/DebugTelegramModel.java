package mvp.model.concrete;

import mvp.model.abs.AbstractTelegramModel;
import org.javagram.dao.DebugTelegramDAO;

/**
 * Created by HerrSergio on 23.08.2016.
 */
public class DebugTelegramModel extends AbstractTelegramModel {
    public DebugTelegramModel() {
        super(new DebugTelegramDAO());
    }
}
