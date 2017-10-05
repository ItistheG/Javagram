package mvp.views.abs.main;

/**
 * Created by HerrSergio on 24.08.2017.
 */
public interface MainViewListener {
    void contactSelectionChanged();
    void sendMessage();
    void search();

    void addContact();
    void editOrDeleteContact();
    void showProfile();
}
