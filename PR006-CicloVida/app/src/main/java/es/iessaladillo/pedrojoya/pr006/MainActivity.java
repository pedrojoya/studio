package es.iessaladillo.pedrojoya.pr006;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_EVENT_LIST = "STATE_EVENT_LIST";

    private TextView lblEventList;

    private String mEventList = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lblEventList = findViewById(R.id.lblEventList);
        showEvent(getString(R.string.main_activity_oncreate));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showEvent(getString(R.string.main_activity_ondestroy));
    }

    @Override
    protected void onPause() {
        super.onPause();
        showEvent(getString(R.string.main_activity_onpause));
    }

    @Override
    protected void onResume() {
        super.onResume();
        showEvent(getString(R.string.main_activity_onresume));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_EVENT_LIST, mEventList);
        showEvent(getString(R.string.main_activity_onsaveinstancestate));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mEventList = savedInstanceState.getString(STATE_EVENT_LIST);
        showEvent(getString(R.string.main_activity_onrestoreinstancestate));
    }

    @Override
    protected void onStart() {
        super.onStart();
        showEvent(getString(R.string.main_activity_onstart));
    }

    @Override
    protected void onStop() {
        super.onStop();
        showEvent(getString(R.string.main_activity_onstop));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showEvent(getString(R.string.main_activity_onrestart));
    }

    private void showEvent(String event) {
        Log.d(getString(R.string.app_name), event);
        mEventList = mEventList.concat(event);
        lblEventList.setText(mEventList);
    }

}
