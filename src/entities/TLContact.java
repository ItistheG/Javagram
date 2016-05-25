package entities;

import org.javagram.response.object.User;

/**
 * Created by HerrSergio on 05.04.2016.
 */
public class TLContact {

    private User user;

    TLContact(User user) {
        this.user = user;
    }

    public String getPhoneNumber() {
        return user.getPhone();
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public int getId() {
        return getId();
    }
}
