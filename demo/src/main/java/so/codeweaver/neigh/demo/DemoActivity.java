package so.codeweaver.neigh.demo;

import android.app.Activity;
import android.os.Bundle;
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
import so.codeweaver.neigh.MediaService;


public class DemoActivity extends Activity {

    @InjectView(so.codeweaver.neighdemo.R.id.demo_action_spinner)
    Spinner actionSpinner;
    @InjectView(so.codeweaver.neighdemo.R.id.demo_song_spinner)
    Spinner songSpinner;
    @InjectView(so.codeweaver.neighdemo.R.id.demo_intent_button)
    Button  intentButton;

    private List<CharSequence> intentNames;
    private List<CharSequence> rawNames;
    private List<Integer>      rawValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(so.codeweaver.neighdemo.R.layout.activity_demo);
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

        Class<so.codeweaver.neighdemo.R.raw> clazz = so.codeweaver.neighdemo.R.raw.class;
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
        getMenuInflater().inflate(so.codeweaver.neighdemo.R.menu.demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == so.codeweaver.neighdemo.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
