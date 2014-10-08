package so.codeweaver.neigh.demo;

import android.app.Activity;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import so.codeweaver.neigh.MediaService;
import so.codeweaver.neigh.ServiceClient;


public class DemoActivity extends Activity implements ServiceClient {

    @InjectView(R.id.demo_action_spinner)
    Spinner actionSpinner;
    @InjectView(R.id.demo_song_spinner)
    Spinner songSpinner;

    private List<CharSequence> intentNames;
    private List<CharSequence> rawNames;
    private List<Integer>      rawValues;

    private ServiceConnection connection;
    private MediaService.MediaBinder mediaBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(so.codeweaver.neigh.demo.R.layout.activity_demo);
        ButterKnife.inject(this);

        intentNames = Arrays.asList(
                MediaService.ACTION_PLAY,
                MediaService.ACTION_PAUSE,
                MediaService.ACTION_STOP,
                MediaService.ACTION_PREV,
                MediaService.ACTION_NEXT
        );
        ArrayAdapter<CharSequence> intentAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, intentNames);
        intentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionSpinner.setAdapter(intentAdapter);

        rawNames = new ArrayList<>();
        rawValues = new ArrayList<>();

        Class<R.raw> clazz = R.raw.class;
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            rawNames.add(field.getName());
            try {
                rawValues.add(field.getInt(null));
            } catch(IllegalAccessException e) {
                // If we can't get a public static final field, then give up, roll over and die
                throw new RuntimeException();
            }
        }

        ArrayAdapter<CharSequence> rawAdapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item, rawNames);
        rawAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        songSpinner.setAdapter(rawAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.demo, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent serviceIntent = new Intent(this, MediaService.class);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DemoActivity.this.mediaBinder = (MediaService.MediaBinder) service;
                DemoActivity.this.mediaBinder.setServiceClient(DemoActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                DemoActivity.this.mediaBinder = null;
            }
        };
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }

    @OnClick(R.id.demo_intent_button)
    public void buttonPressed() {
        int actionIndex = actionSpinner.getSelectedItemPosition();
        int mediaIndex = songSpinner.getSelectedItemPosition();

        Intent i = new Intent(this, MediaService.class);
        i.setAction(String.valueOf(intentNames.get(actionIndex)));
        i.setData(Uri.parse("android.resource://so.codeweaver.neigh.demo/" + rawValues.get(mediaIndex)));

        startService(i);
    }

    @Override
    public Notification provideForegroundNotification(Uri uri) {
        return MediaPlayingNotification.buildNotification(this, "test", 42);
    }
}
