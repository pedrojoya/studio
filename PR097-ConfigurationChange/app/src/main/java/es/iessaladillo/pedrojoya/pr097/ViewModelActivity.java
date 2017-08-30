package es.iessaladillo.pedrojoya.pr097;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

@SuppressWarnings("FieldCanBeLocal")
public class ViewModelActivity extends AppCompatActivity {

    private TextView lblCount;
    private Button btnIncrement;

    private State mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        mState = ViewModelProviders.of(this).get(State.class);
        initViews();
        showCount();
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

    private void showCount() {
        lblCount.setText(String.valueOf(mState.getCount()));
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ViewModelActivity.class));
    }

    static class State extends ViewModel {
        private int count = 0;

        public int getCount() {
            return count;
        }

        public void increment() {
            count++;
        }
    }

}
