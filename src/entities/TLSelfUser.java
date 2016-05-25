package entities;

import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.object.UserContact;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by HerrSergio on 05.04.2016.
 */
public class TLSelfUser implements Closeable {

    private AuthAuthorization auth;
    private TelegramApiBridge bridge;
    private String phoneNumber;
    private TLContact contact;


    public TLSelfUser(String phoneNumber) throws IOException {
        this(new TelegramApiBridge("149.154.167.50:443", 23546, "02a2c6e304647e51f0ccfef791fdfb5e"), phoneNumber);
    }

    public TLSelfUser(TelegramApiBridge bridge, String phoneNumber) {
        this.bridge = bridge;
        this.phoneNumber = phoneNumber;
    }

    public void sendCode() throws IOException {
        bridge.authSendCode(phoneNumber);
    }

    public void signIn(String code) throws IOException {
        if(auth != null)
            throw new IllegalStateException();
        auth = bridge.authSignIn(code);
        contact = new TLContact(auth.getUser());
    }

    public void logOut() {
        try {
            if(!hasLoggedIn())
                throw new IllegalStateException("Not logged in!");
            auth = null;
            contact = null;
            bridge.authLogOut();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<TLUser> getUsers() throws IOException {
        if(auth == null)
            throw new IllegalStateException();
        ArrayList<UserContact> contacts = bridge.contactsGetContacts();
        ArrayList<TLUser> ret = new ArrayList<>();
        for(UserContact contact : contacts)
            ret.add(new TLUser(contact));
        return ret;
    }

    public TLContact getContact() {
        return contact;
    }

    public boolean hasLoggedIn() {
        return auth != null;
    }

    @Override
    public void close() throws IOException {
        if(hasLoggedIn())
            logOut();
    }
}
