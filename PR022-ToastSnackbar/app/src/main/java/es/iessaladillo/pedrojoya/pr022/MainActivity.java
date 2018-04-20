package es.iessaladillo.pedrojoya.pr022;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.tapadoo.alerter.Alerter;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private TextView lblText;
    private Button btnToast;
    private Button btnToastLayout;
    private Button btnSnackbar;
    private Button btnStylableToast;
    private Button btnAlerter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        lblText = ActivityCompat.requireViewById(this, R.id.lblText);
        btnToast = ActivityCompat.requireViewById(this, R.id.btnToast);
        btnToastLayout = ActivityCompat.requireViewById(this, R.id.btnToastLayout);
        btnSnackbar = ActivityCompat.requireViewById(this, R.id.btnSnackbar);
        btnStylableToast = ActivityCompat.requireViewById(this, R.id.btnStylableToast);
        btnAlerter = ActivityCompat.requireViewById(this, R.id.btnAlerter);

        btnToast.setOnClickListener(
                v -> showToast(getString(R.string.main_activity_toast_message)));
        btnToastLayout.setOnClickListener(
                v -> showToastLayout(R.string.main_activity_layout_message, R.layout.toast,
                        R.id.lblMessage));
        btnSnackbar.setOnClickListener(v -> {
            changeVisibility(lblText);
            showSnackbar(getString(R.string.main_activity_visibility_changed));
        });
        btnStylableToast.setOnClickListener(v -> new StyleableToast.Builder(MainActivity.this).text(
                getString(R.string.main_activity_stylable_message))
                .backgroundColor(Color.RED)
                .textColor(Color.WHITE)
                .iconResLeft(R.drawable.ic_add_alert)
                .show());
        btnAlerter.setOnClickListener(v -> Alerter.create(MainActivity.this)
                .setTitle(R.string.main_activity_attention)
                .setText(R.string.main_activity_alerter_message)
                .setBackgroundColorRes(R.color.accent)
                .show());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Shows a toast that uses an specific layout. Receives the layout resId and the textView
    // resId inside the layout.
    @SuppressWarnings("SameParameterValue")
    private void showToastLayout(int stringResId, @LayoutRes int layoutId, @IdRes int textViewId) {
        try {
            View raiz = LayoutInflater.from(this).inflate(layoutId, null);
            TextView textView = raiz.findViewById(textViewId);
            if (textView != null) {
                textView.setText(stringResId);
                Toast toast = new Toast(this);
                toast.setView(raiz);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            } else {
                // If textView not available, we use an standard toast.
                Toast.makeText(this, stringResId, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // If error, we use an standard toast.
            Toast.makeText(this, stringResId, Toast.LENGTH_LONG).show();
        }
    }

    private void changeVisibility(View view) {
        view.setVisibility(view.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
    }

    private void showSnackbar(String message) {
        Snackbar.make(lblText, message, Snackbar.LENGTH_LONG).setAction(
                getString(R.string.main_activity_undo), view -> changeVisibility(lblText)).show();
    }

}
