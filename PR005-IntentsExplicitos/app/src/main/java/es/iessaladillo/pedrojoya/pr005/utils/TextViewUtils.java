package es.iessaladillo.pedrojoya.pr005.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public class TextViewUtils {

    private TextViewUtils() {
    }

    public interface AfterTextChangedListener {
        void afterTextChanged(Editable s);
    }

    public interface BeforeTextChangedListener {
        void beforeTextChanged(CharSequence s, int start, int count, int after);
    }

    public interface OnTextChangedListener {
        void onTextChanged(CharSequence s, int start, int before, int count);
    }

    public static void addAfterTextChangedListener(@NonNull TextView textView,
            @NonNull AfterTextChangedListener listener) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.afterTextChanged(s);
            }
        });
    }

    public static void addBeforeTextChangedListener(@NonNull TextView textView,
            @NonNull BeforeTextChangedListener listener) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                listener.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public static void addOnTextChangedListener(@NonNull TextView textView,
            @NonNull OnTextChangedListener listener) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listener.onTextChanged(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public static void addTextWatcherListeners(@NonNull TextView textView,
            BeforeTextChangedListener beforeTextChangedListener,
            OnTextChangedListener onTextChangedListener,
            AfterTextChangedListener afterTextChangedListener) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (beforeTextChangedListener != null) {
                    beforeTextChangedListener.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onTextChangedListener != null) {
                    onTextChangedListener.onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (afterTextChangedListener != null) {
                    afterTextChangedListener.afterTextChanged(s);
                }
            }
        });
    }

    public interface OnImeActionDoneListener {
        void onActionDone(TextView v, KeyEvent event);
    }

    public static void setOnImeActionDoneListener(@NonNull TextView textView,
            @NonNull OnImeActionDoneListener listener) {
        textView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                listener.onActionDone(v, event);
                return true;
            }
            return false;
        });
    }

}
