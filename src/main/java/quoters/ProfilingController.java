package quoters;

/**
 * Created by antonnaumoff on 10.09.15.
 */
public class ProfilingController implements ProfilingControllerMBean{
    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
