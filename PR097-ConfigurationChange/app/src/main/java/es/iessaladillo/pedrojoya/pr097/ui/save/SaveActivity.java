package es.iessaladillo.pedrojoya.pr097.ui.save;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr097.R;

@SuppressWarnings("FieldCanBeLocal")
public class SaveActivity extends AppCompatActivity {

    private static final String STATE_COUNT = "STATE_COUNT";
    private static final int COUNT_DEFAULT = 0;

    private TextView lblCount;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        restoreSavedInstanceState(savedInstanceState);
        initViews();
        showCount();
    }

    private void restoreSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            count = savedInstanceState.getInt(STATE_COUNT, COUNT_DEFAULT);
        }
    }

    private void initViews() {
        lblCount = ActivityCompat.requireViewById(this, R.id.lblCount);

        ActivityCompat.requireViewById(this, R.id.btnIncrement).setOnClickListener(v -> increment());
    }

    private void increment() {
        count++;
        showCount();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_COUNT, count);
    }

    private void showCount() {
        lblCount.setText(String.valueOf(count));
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, SaveActivity.class));
    }

}
