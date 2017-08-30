package es.iessaladillo.pedrojoya.pr097;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;
import activitystarter.Optional;

@SuppressWarnings("FieldCanBeLocal")
@MakeActivityStarter
public class StarterActivity extends AppCompatActivity {

    private TextView lblCount;
    private Button btnIncrement;

    @SuppressWarnings("WeakerAccess")
    @Arg
    @Optional
    int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        ActivityStarter.fill(this, savedInstanceState);
        initViews();
        showCount();
    }

    private void initViews() {
        lblCount = findViewById(R.id.lblCount);
        btnIncrement = findViewById(R.id.btnIncrement);

        btnIncrement.setOnClickListener(v -> increment());
    }

    private void increment() {
        mCount++;
        showCount();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ActivityStarter.save(this, outState);
    }

    private void showCount() {
        lblCount.setText(String.valueOf(mCount));
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, StarterActivity.class));
    }

}
