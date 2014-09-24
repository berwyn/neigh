package so.codeweaver.neigh;

import android.app.Notification;
import android.net.Uri;

/**
 * Created by berwyn on 23/09/14.
 */
public interface ServiceClient {

    /**
     * The {@link so.codeweaver.neigh.MediaService} is beginning playback and would like
     * the client to provide a notification for the foreground.
     *
     * @param uri The uri being played back
     * @return A {@link android.app.Notification} to display in the foreground
     */
    public Notification provideForegroundNotification(Uri uri);

}
