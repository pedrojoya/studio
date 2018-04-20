package es.iessaladillo.pedrojoya.pr097;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import icepick.Icepick;
import icepick.State;

@SuppressWarnings("FieldCanBeLocal")
public class IcepickActivity extends AppCompatActivity {

    private TextView lblCount;
    private Button btnIncrement;

    @SuppressWarnings("WeakerAccess")
    @State
    int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        Icepick.restoreInstanceState(this, savedInstanceState);
        initViews();
        showCount();
    }

    private void initViews() {
        lblCount = ActivityCompat.requireViewById(this, R.id.lblCount);
        btnIncrement = ActivityCompat.requireViewById(this, R.id.btnIncrement);

        btnIncrement.setOnClickListener(v -> increment());
    }

    private void increment() {
        mCount++;
        showCount();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    private void showCount() {
        lblCount.setText(String.valueOf(mCount));
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, IcepickActivity.class));
    }

}
