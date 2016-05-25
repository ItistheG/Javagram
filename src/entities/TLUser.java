package entities;

import org.javagram.response.object.UserContact;

/**
 * Created by HerrSergio on 05.04.2016.
 */
public class TLUser extends TLContact {

    private UserContact userContact;

    protected TLUser(UserContact user) {
        super(user);
    }

    public boolean isOnline() {
        return userContact.isOnline();
    }
}
