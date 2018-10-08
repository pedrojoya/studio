package es.iessaladillo.pedrojoya.pr010.ui.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
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
        setupView();
    }

    private void setupView() {
        lblText = ActivityCompat.requireViewById(this, R.id.lblText);
        txtMessage = ActivityCompat.requireViewById(this, R.id.txtMessage);
        btnSend = ActivityCompat.requireViewById(this, R.id.btnSend);
        scvText = ActivityCompat.requireViewById(this, R.id.scvText);

        txtMessage.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendMessage(txtMessage.getText().toString());
                return true;
            }
            return false;
        });
        txtMessage.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIsValidForm();
            }

        });
        btnSend.setOnClickListener(v -> sendMessage(txtMessage.getText().toString()));
        // Initial state
        checkIsValidForm();
        doScroll(scvText);
    }

    private void sendMessage(@NonNull String text) {
        if (!TextUtils.isEmpty(text)) {
            KeyboardUtils.hideSoftKeyboard(this);
            lblText.append(getString(R.string.main_log_message, simpleDateFormat.format(new Date()), text));
            txtMessage.setText("");
            doScroll(scvText);
        }
    }

    private void doScroll(@NonNull final NestedScrollView scv) {
        // Must be posted in order to calculate the end position correctly.
        scv.post(() -> {
            scv.smoothScrollTo(0, scv.getBottom());
            txtMessage.requestFocus();
        });
    }

    private void checkIsValidForm() {
        btnSend.setEnabled(!TextUtils.isEmpty(txtMessage.getText()));
    }

}
