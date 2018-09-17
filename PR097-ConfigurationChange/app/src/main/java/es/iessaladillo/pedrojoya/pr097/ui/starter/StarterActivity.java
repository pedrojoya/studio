package es.iessaladillo.pedrojoya.pr097.ui.starter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import es.iessaladillo.pedrojoya.pr097.R;

@MakeActivityStarter
public class StarterActivity extends AppCompatActivity {

    private TextView lblCount;

    @SuppressWarnings("WeakerAccess")
    @Arg(optional = true)
    public int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        ActivityStarter.fill(this, savedInstanceState);
        initViews();
        showCount();
    }

    private void initViews() {
        lblCount = ActivityCompat.requireViewById(this, R.id.lblCount);

        ActivityCompat.requireViewById(this, R.id.btnIncrement)
                .setOnClickListener(v -> increment());
    }

    private void increment() {
        count++;
        showCount();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ActivityStarter.save(this, outState);
    }

    private void showCount() {
        lblCount.setText(String.valueOf(count));
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, StarterActivity.class));
    }

}
