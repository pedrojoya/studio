package es.iessaladillo.pedrojoya.pr097.ui.viewmodel;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr097.R;

public class ViewModelActivity extends AppCompatActivity {

    private TextView lblCount;

    private ViewModelActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        viewModel = ViewModelProviders.of(this).get(ViewModelActivityViewModel.class);
        initViews();
        showCount();
    }

    private void initViews() {
        lblCount = ActivityCompat.requireViewById(this, R.id.lblCount);

        ActivityCompat.requireViewById(this, R.id.btnIncrement)
                .setOnClickListener(v -> increment());
    }

    private void increment() {
        viewModel.increment();
        showCount();
    }

    private void showCount() {
        lblCount.setText(String.valueOf(viewModel.getCount()));
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ViewModelActivity.class));
    }

}
