package es.iessaladillo.pedrojoya.pr010;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText txtMessage;
    private TextView lblText;
    private ImageView btnSend;
    private NestedScrollView scvText;

    private SimpleDateFormat mDateFormatter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mDateFormatter = new SimpleDateFormat("HH:mm:ss",
                Locale.getDefault());
    }

    private void initViews() {
        lblText = findViewById(R.id.lblText);
        txtMessage = findViewById(R.id.txtMessage);
        btnSend = findViewById(R.id.btnSend);
        scvText = findViewById(R.id.scvText);

        txtMessage.setHorizontallyScrolling(false);
        txtMessage.setMaxLines(getResources().getInteger(R.integer.main_activity_txtMensaje_maxLines));
        txtMessage.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        txtMessage.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendMessage(txtMessage.getText().toString());
                return true;
            }
            return false;
        });
        txtMessage.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIsValidForm();
            }

        });
        btnSend.setOnClickListener(v -> sendMessage(txtMessage.getText().toString()));
        // Initial state
        checkIsValidForm();
        doScroll(scvText, View.FOCUS_DOWN);
    }

    private void sendMessage(String text) {
        if (!TextUtils.isEmpty(text)) {
            String time = mDateFormatter.format(new Date());
            lblText.append(getString(R.string.main_activity_log_message, time, text));
            txtMessage.setText("");
            doScroll(scvText, View.FOCUS_DOWN);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void doScroll(final NestedScrollView scv, final int focus) {
        // Must be posted in order to calculate the end position correctly.
        scv.post(() -> {
            scv.fullScroll(focus);
            // scv.scrollTo(0, scv.getBottom());
            txtMessage.requestFocus();
        });
    }

    private void checkIsValidForm() {
        btnSend.setEnabled(!TextUtils.isEmpty(txtMessage.getText()));
    }

}
