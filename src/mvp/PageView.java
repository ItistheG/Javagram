package mvp;

/**
 * Created by HerrSergio on 23.08.2016.
 */
public interface PageView {
    void setView(View view);
    View getView();
    void setOverlay(Overlay overlay);
    Overlay getOverlay();
}
