package mvp.model.abs;

import org.javagram.dao.*;
import org.javagram.dao.proxy.changes.*;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;

/**
 * Created by HerrSergio on 23.08.2016.
 */
public interface TelegramModel extends AutoCloseable {
    public Status getStatus();
    public void acceptNumber(String phoneNumber) throws IOException, ApiException;
    public void sendCode() throws IOException, ApiException;

    public void acceptAuthCode(String authCode);
    public String getAuthCode();

    public void acceptName(String firstName, String lastName);
    public String getAuthFirstName();
    public String getAuthLastName();

    public void acceptNumberAndSendCode(String phoneNumber) throws IOException, ApiException;

    public String getPhoneNumber();

    public void signIn(String code) throws IOException, ApiException;
    default void signIn() throws IOException, ApiException {
        signIn(getAuthCode());
    }
    public void signUp(String code, String firstName, String lastName) throws IOException, ApiException;
    default void signUp() throws IOException, ApiException {
        signUp(getAuthCode(), getAuthFirstName(), getAuthLastName());
    }
    public void logOut() throws IOException, ApiException;
    public boolean isLoggedIn();
    public boolean canSignUp();
    public boolean canSignIn();
    public boolean isClosed();


    public void close() throws IOException, ApiException;

    public State getState() throws IOException, ApiException;

    public List<Person> getPersons();
    public Dialog getDialog(Person person);

    default Person getPerson(int id) {
        for(Person person : getPersons()) {
            if(person.getId() == id)
                return person;
        }
        return null;
    }

    public Map<Person, Dialog> getDialogs(boolean includeEmpty);

    public List<Message> getMessages(Person person, int count);

    public int getAvailableMessagesCount(Person person);

    public void squeezeMessages(Person person, int count);

    public UpdateChanges update();

    public UpdateChanges update(int updateStyle);

    public Me getMe();

    public Date onlineUntil(Person person);

    public boolean isOnline(Person person);

    public BufferedImage getPhoto(Person person, boolean small) throws IOException, ApiException;

    default BufferedImage getPhotoNoThrow(Person person, boolean small) {
        try {
            return getPhoto(person, small);
        } catch (Exception e) {
            return null;
        }
    }

    default Map<Person, BufferedImage> getPhotosNoThrow(List<Person> persons, boolean small) {
        Map<Person, BufferedImage> map = new HashMap<>();
        for (Person person : persons) {
            try {
                BufferedImage photo = getPhoto(person, small);
                if(photo != null)
                    map.put(person, photo);
            } catch (Exception e) {

            }
        }
        return map;
    }

    default Map<Person, BufferedImage> getPhotosNoThrow(boolean small) {
        return getPhotosNoThrow(getPersons(), small);
    }

    public void sendMessage(Person person, String text, long randomId) throws IOException, ApiException;

    public long sendMessage(Person person, String text) throws IOException, ApiException;

    public void readMessages(Message lastMessage) throws IOException, ApiException;

    public void receivedMessages(Message lastMessage) throws IOException, ApiException;

    public void importContact(String phone, String firstName, String lastName) throws IOException, ApiException;

    public void deleteContact(Contact contact) throws IOException, ApiException;

    public void deleteContact(int contactId) throws IOException, ApiException;

    default Set<Person> online(List<Person> persons) {
        HashSet<Person> set = new HashSet<>();
        for(Person person : persons)
            if(isOnline(person))
                set.add(person);
        return set;
    }

    default Set<Person> online() {
        return online(getPersons());
    }

    public Integer getSelectedContactId();
    public void setSelectedContactId(Integer id);

    public void addSelectedContactIdChangeListener(PropertyChangeListener listener);
    public void removeSelectedContactIdChangeListener(PropertyChangeListener listener);

    public void addObserver(Observer observer) ;
    public void deleteObserver(Observer observer);

    void update(boolean force);
}
