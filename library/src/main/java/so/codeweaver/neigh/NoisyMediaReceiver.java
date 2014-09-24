package so.codeweaver.neigh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NoisyMediaReceiver extends BroadcastReceiver {
    public NoisyMediaReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()
                .equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
            Intent stopIntent = new Intent(MediaService.ACTION_STOP);
            stopIntent.setClass(context, MediaService.class);
            context.startService(stopIntent);
        }
    }
}
