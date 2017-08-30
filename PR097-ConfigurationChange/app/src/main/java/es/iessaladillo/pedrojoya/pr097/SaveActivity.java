package es.iessaladillo.pedrojoya.pr097;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

@SuppressWarnings("FieldCanBeLocal")
public class SaveActivity extends AppCompatActivity {

    private static final String STATE_COUNT = "STATE_COUNT";
    private static final int COUNT_DEFAULT = 0;

    private TextView lblCount;
    private Button btnIncrement;

    private int mCount;

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
            mCount = savedInstanceState.getInt(STATE_COUNT, COUNT_DEFAULT);
        }
    }

    private void initViews() {
        lblCount = findViewById(R.id.lblCount);
        btnIncrement = findViewById(R.id.btnIncrement);

        btnIncrement.setOnClickListener(v -> incrementarContador());
    }

    private void incrementarContador() {
        mCount++;
        showCount();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_COUNT, mCount);
    }

    private void showCount() {
        lblCount.setText(String.valueOf(mCount));
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, SaveActivity.class));
    }

}
