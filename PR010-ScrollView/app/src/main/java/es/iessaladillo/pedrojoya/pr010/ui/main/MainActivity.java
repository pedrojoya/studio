package es.iessaladillo.pedrojoya.pr010.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import es.iessaladillo.pedrojoya.pr010.R;
import es.iessaladillo.pedrojoya.pr010.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr010.utils.TextViewUtils;

public class MainActivity extends AppCompatActivity {

    private EditText txtMessage;
    private TextView lblText;
    private ImageView btnSend;
    private NestedScrollView scvText;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss",
            Locale.getDefault());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        lblText = ActivityCompat.requireViewById(this, R.id.lblText);
        txtMessage = ActivityCompat.requireViewById(this, R.id.txtMessage);
        btnSend = ActivityCompat.requireViewById(this, R.id.btnSend);
        scvText = ActivityCompat.requireViewById(this, R.id.scvText);

        TextViewUtils.setOnImeActionDoneListener(txtMessage, (v, event) -> sendMessage());
        TextViewUtils.addAfterTextChangedListener(txtMessage, s -> checkIsValidForm());
        btnSend.setOnClickListener(v -> sendMessage());
        checkInitialState();
    }

    private void checkInitialState() {
        checkIsValidForm();
        doScroll(scvText);
    }

    private void sendMessage() {
        String text = txtMessage.getText().toString();
        if (isValidForm()) {
            KeyboardUtils.hideSoftKeyboard(this);
            lblText.append(getString(R.string.main_log_message, simpleDateFormat.format(new Date()), text));
            txtMessage.setText("");
            doScroll(scvText);
        }
    }

    private boolean isValidForm() {
        return !TextUtils.isEmpty(txtMessage.getText().toString());
    }

    private void doScroll(@NonNull final NestedScrollView scv) {
        // Must be posted in order to calculate the end position correctly.
        scv.post(() -> {
            scv.smoothScrollTo(0, scv.getBottom());
            txtMessage.requestFocus();
        });
    }

    private void checkIsValidForm() {
        btnSend.setEnabled(isValidForm());
    }

}
