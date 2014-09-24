package so.codeweaver.neigh;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.media.MediaRouter;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class MediaService extends Service implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnErrorListener,
        AudioManager.OnAudioFocusChangeListener {

    public static final String TAG_WIFI_LOCK = "so.codeweaver.neigh.WIFI_LOCK";
    public static final int TAG_FOREGROUND = 1337;
    public static final String ACTION_PLAY = "so.codeweaver.neigh.PLAY";
    public static final String ACTION_STOP = "so.codeweaver.neigh.STOP";

    private MediaPlayer player;
    private MediaBinder binder;
    private MediaRouter mediaRouter;
    private ServiceClient client;
    private WifiManager.WifiLock wifiLock;

    private Queue<Uri> playQueue;

    public MediaService() {
        playQueue = new ArrayDeque<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        switch (intent.getAction()) {
            case ACTION_PLAY:
                handleActionPlay(intent);
                break;
        }

        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        if(binder == null) {
            binder = new MediaBinder();
        }
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player != null) {
            destroyMediaPlayer();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Notification notification = client.provideForegroundNotification(playQueue.peek());
        startForeground(TAG_FOREGROUND, notification);
        mp.start();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        // TODO: Handle this
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // TODO: Handle this
        destroyMediaPlayer();
        return false;
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch(focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // We've gained focus, so we should start playback, initialising the player
                // as needed.
                if(player == null) {
                    initMediaPlayer();
                } else if(!player.isPlaying()) {
                    onPrepared(player);
                }
                player.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // We've lost focus for an unknown amount of time
                destroyMediaPlayer();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // We've temporarily lost focus, and need to stop
                // We should /not/ release the player because we will resume soon
                if(player != null) {
                    player.pause();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // We've lost focus for a short time, but can continue playing with ducked volume
                if(player.isPlaying()) player.setVolume(0.1f, 0.1f);
                break;
        }
    }

    private MediaPlayer initMediaPlayer() {
        MediaPlayer mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mp.setOnPreparedListener(this);
        mp.setOnBufferingUpdateListener(this);
        mp.setOnErrorListener(this);

        wifiLock = ((WifiManager) getSystemService(WIFI_SERVICE))
                .createWifiLock(WifiManager.WIFI_MODE_FULL, TAG_WIFI_LOCK);
        wifiLock.acquire();

        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if(result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // TODO: Move to error state
            return null;
        }

        return mp;
    }

    private void destroyMediaPlayer() {
        wifiLock.release();
    }

    private void handleActionPlay(Intent intent) {
        if(intent.getData() == null) {
            throw new IllegalStateException("You cannot use a null Uri");
        }

        Uri uri = intent.getData();
        playQueue.clear();
        playQueue.add(uri);

        try {
            player = initMediaPlayer();
            if(player == null) return;
            player.setDataSource(getApplicationContext(), uri);
            player.prepareAsync();
        } catch (IOException e) {
            // TODO: Real logic
            throw new RuntimeException("I just don't know what went wrong D:");
        }
    }

    public class MediaBinder extends Binder {

        public MediaRouter getMediaRouter() {
            return MediaService.this.mediaRouter;
        }

        public void setMediaRouter(MediaRouter mediaRouter) {
            MediaService.this.mediaRouter = mediaRouter;
        }

        public void setServiceClient(ServiceClient client) {
            MediaService.this.client = client;
        }

    }
}
