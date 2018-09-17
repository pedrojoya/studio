package es.iessaladillo.pedrojoya.pr097.ui.retain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr097.R;

@SuppressWarnings("FieldCanBeLocal")
public class RetainActivity extends AppCompatActivity {

    private TextView lblCount;

    private State state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        restoreLastCustomNonConfigurationIntance();
        initViews();
        showCount();
    }

    private void restoreLastCustomNonConfigurationIntance() {
        state = (State) getLastCustomNonConfigurationInstance();
        if (state == null) {
            state = new State();
        }
    }

    private void initViews() {
        lblCount = ActivityCompat.requireViewById(this, R.id.lblCount);

        ActivityCompat.requireViewById(this, R.id.btnIncrement).setOnClickListener(v -> increment());
    }

    private void increment() {
        state.increment();
        showCount();
    }

    @Override
    public State onRetainCustomNonConfigurationInstance() {
        return state;
    }

    private void showCount() {
        lblCount.setText(String.valueOf(state.getCount()));
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, RetainActivity.class));
    }

    private static class State {
        private int count = 0;

        int getCount() {
            return count;
        }

        void increment() {
            count++;
        }
    }

}
