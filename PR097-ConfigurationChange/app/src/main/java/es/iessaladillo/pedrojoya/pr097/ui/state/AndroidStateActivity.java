package es.iessaladillo.pedrojoya.pr097.ui.state;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.evernote.android.state.State;
import com.evernote.android.state.StateSaver;

import es.iessaladillo.pedrojoya.pr097.R;

public class AndroidStateActivity extends AppCompatActivity {

    private TextView lblCount;

    @SuppressWarnings("WeakerAccess")
    @State
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        StateSaver.restoreInstanceState(this, savedInstanceState);
        initViews();
        showCount();
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
        StateSaver.saveInstanceState(this, outState);
    }

    private void showCount() {
        lblCount.setText(String.valueOf(count));
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, AndroidStateActivity.class));
    }

}
