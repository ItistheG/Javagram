package mvp.model.abs;

import org.javagram.dao.*;
import org.javagram.dao.proxy.TelegramProxy;
import org.javagram.dao.proxy.changes.UpdateChanges;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.*;

/**
 * Created by HerrSergio on 23.08.2016.
 */
public abstract class AbstractTelegramModel extends Observable implements TelegramModel {

    private String code;
    private String firstName;
    private String lastName;

    private TelegramDAO telegramDAO;
    private TelegramProxy telegramProxy;

    private Integer selectedContactId;

    private void checkProxy(boolean exist) {
        if(telegramProxy == null && exist || telegramProxy != null && !exist)
            throw new IllegalStateException();
    }

    public AbstractTelegramModel(TelegramDAO telegramDAO) {
        this.telegramDAO = telegramDAO;
    }

    @Override
    public Status getStatus() {
        return telegramDAO.getStatus();
    }

    @Override
    public void acceptNumber(String phoneNumber) throws IOException, ApiException {
        checkProxy(false);
        telegramDAO.acceptNumber(phoneNumber);
    }

    @Override
    public void sendCode() throws IOException, ApiException {
        checkProxy(false);
        telegramDAO.sendCode();
    }

    @Override
    public void acceptNumberAndSendCode(String phoneNumber) throws IOException, ApiException {
        checkProxy(false);
        telegramDAO.acceptNumberAndSendCode(phoneNumber);
    }

    @Override
    public String getPhoneNumber() {
        return telegramDAO.getPhoneNumber();
    }

    @Override
    public void signIn(String code) throws IOException, ApiException {
        checkProxy(false);
        Me me = telegramDAO.signIn(code);
        telegramProxy = new TelegramProxy(telegramDAO);
        initMe(me);
        //return me;
    }

    @Override
    public void signUp(String code, String firstName, String lastName) throws IOException, ApiException {
        checkProxy(false);
        Me me = telegramDAO.signUp(code, firstName, lastName);
        telegramProxy = new TelegramProxy(telegramDAO);
        initMe(me);
        //return me;
    }

    protected void initMe(Me me) {
        firstName = me.getFirstName();
        lastName = me.getLastName();
        code = null;
    }

    @Override
    public void logOut() throws IOException, ApiException {
        telegramProxy = null;
        telegramDAO.logOut();
    }

    @Override
    public boolean isLoggedIn() {
        return telegramDAO.isLoggedIn();
    }

    @Override
    public boolean canSignUp() {
        return telegramDAO.canSignUp();
    }

    @Override
    public boolean canSignIn() {
        return telegramDAO.canSignUp();
    }

    @Override
    public boolean isClosed() {
        return telegramDAO.isClosed();
    }

    @Override
    public void close() throws IOException, ApiException  {
        telegramProxy = null;
        telegramDAO.close();
    }

    @Override
    public State getState() throws IOException, ApiException {
        return telegramDAO.getState();
    }

    @Override
    public List<Person> getPersons() {
        checkProxy(true);
        return telegramProxy.getPersons();
    }

    @Override
    public Dialog getDialog(Person person) {
        checkProxy(true);
        return telegramProxy.getDialog(person);
    }

    @Override
    public Map<Person, Dialog> getDialogs(boolean includeEmpty) {
        checkProxy(true);
        return telegramProxy.getDialogs(includeEmpty);
    }

    @Override
    public List<Message> getMessages(Person person, int count) {
        checkProxy(true);
        return telegramProxy.getMessages(person, count);
    }

    @Override
    public int getAvailableMessagesCount(Person person) {
        checkProxy(true);
        return telegramProxy.getAvailableMessagesCount(person);
    }

    @Override
    public void squeezeMessages(Person person, int count) {
        checkProxy(true);
        telegramProxy.squeezeMessages(person, count);
    }

    @Override
    public UpdateChanges update() {
        checkProxy(true);
        return telegramProxy.update();
    }

    @Override
    public UpdateChanges update(int updateStyle) {
        checkProxy(true);
        return telegramProxy.update(updateStyle);
    }

    @Override
    public Me getMe() {
        checkProxy(true);
        return telegramProxy.getMe();
    }

    @Override
    public Date onlineUntil(Person person) {
        checkProxy(true);
        return telegramProxy.onlineUntil(person);
    }

    @Override
    public boolean isOnline(Person person) {
        checkProxy(true);
        return telegramProxy.isOnline(person);
    }

    @Override
    public BufferedImage getPhoto(Person person, boolean small) throws IOException, ApiException {
        checkProxy(true);
        return telegramProxy.getPhoto(person, small);
    }

    @Override
    public void sendMessage(Person person, String text, long randomId) throws IOException, ApiException {
        checkProxy(true);
        telegramProxy.sendMessage(person, text, randomId);
    }

    @Override
    public long sendMessage(Person person, String text) throws IOException, ApiException {
        checkProxy(true);
        return telegramProxy.sendMessage(person, text);
    }

    @Override
    public void readMessages(Message lastMessage) throws IOException, ApiException {
        checkProxy(true);
        telegramProxy.readMessages(lastMessage);
    }

    @Override
    public void receivedMessages(Message lastMessage) throws IOException, ApiException {
        checkProxy(true);
        telegramProxy.receivedMessages(lastMessage);
    }

    @Override
    public void importContact(String phone, String firstName, String lastName) throws IOException, ApiException {
        checkProxy(true);
        telegramProxy.importContact(phone, firstName, lastName);
    }

    @Override
    public void deleteContact(Contact contact) throws IOException, ApiException {
        checkProxy(true);
        telegramProxy.deleteContact(contact);
    }

    @Override
    public void deleteContact(int contactId) throws IOException, ApiException {
        checkProxy(true);
        telegramProxy.deleteContact(contactId);
    }

    @Override
    public Integer getSelectedContactId() {
        return selectedContactId;
    }

    @Override
    public void setSelectedContactId(Integer id) {
        if(!Objects.equals(selectedContactId, id)) {
            Integer oldValue = this.selectedContactId;
            this.selectedContactId = id;
            this.pcs.firePropertyChange("selectedContactId", oldValue, id);
        }
    }

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addSelectedContactIdChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener("selectedContactId", listener);
    }

    public void removeSelectedContactIdChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener("selectedContactId", listener);
    }


    @Override
    public void acceptAuthCode(String authCode) {
        checkProxy(false);
        code = authCode;
    }

    @Override
    public String getAuthCode() {
        return code;
    }

    @Override
    public void acceptName(String firstName, String lastName) {
        checkProxy(false);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String getAuthFirstName() {
        return firstName;
    }

    @Override
    public String getAuthLastName() {
        return lastName;
    }

    @Override
    public void update(boolean force) {
        if(telegramProxy != null) {
            UpdateChanges updateChanges = telegramProxy.update(force ? TelegramProxy.FORCE_SYNC_UPDATE : TelegramProxy.USE_SYNC_UPDATE);

            Integer currentBuddyId = getSelectedContactId();
            if(currentBuddyId != null && getPerson(currentBuddyId) == null) {
                setSelectedContactId(null);
            }

            this.setChanged();
            this.notifyObservers(updateChanges);
        }
    }
}
