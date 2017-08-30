package es.iessaladillo.pedrojoya.pr097;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

@SuppressWarnings("FieldCanBeLocal")
public class RetainActivity extends AppCompatActivity {

    private TextView lblCount;
    private Button btnIncrement;

    private State mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        restoreLastCustomNonConfigurationIntance();
        initViews();
        showCount();
    }

    private void restoreLastCustomNonConfigurationIntance() {
        mState = (State) getLastCustomNonConfigurationInstance();
        if (mState == null) {
            mState = new State();
        }
    }

    private void initViews() {
        lblCount = findViewById(R.id.lblCount);
        btnIncrement = findViewById(R.id.btnIncrement);

        btnIncrement.setOnClickListener(v -> increment());
    }

    private void increment() {
        mState.increment();
        showCount();
    }

    @Override
    public State onRetainCustomNonConfigurationInstance() {
        return mState;
    }

    private void showCount() {
        lblCount.setText(String.valueOf(mState.getCount()));
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, RetainActivity.class));
    }

    private static class State {
        private int count = 0;

        public int getCount() {
            return count;
        }

        public void increment() {
            count++;
        }
    }

}
