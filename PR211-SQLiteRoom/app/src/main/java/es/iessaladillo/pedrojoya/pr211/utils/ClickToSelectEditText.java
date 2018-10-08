package es.iessaladillo.pedrojoya.pr211.utils;

import android.content.Context;
import android.graphics.Canvas;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;

@SuppressWarnings("unchecked")
public class ClickToSelectEditText<T> extends AppCompatEditText {

    private final CharSequence mHint;
    private OnItemSelectedListener<T> onItemSelectedListener;
    private ListAdapter mSpinnerAdapter;

    public ClickToSelectEditText(Context context) {
        super(context);
        mHint = getHint();
    }

    public ClickToSelectEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHint = getHint();
    }

    public ClickToSelectEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHint = getHint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setFocusable(true);
        setClickable(true);
        setInputType(InputType.TYPE_NULL);
        setKeyListener(null);
    }

    public void setAdapter(ListAdapter adapter) {
        mSpinnerAdapter = adapter;
        configureOnClickListener();
    }


    private void configureOnClickListener() {
        setOnClickListener(this::showDialog);
        setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                showDialog(view);
            }
        });
    }

    @SuppressWarnings("WeakerAccess")
    public void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(mHint);
        builder.setAdapter(mSpinnerAdapter, (dialogInterface, selectedIndex) -> {
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelectedListener(
                        (T) mSpinnerAdapter.getItem(selectedIndex), selectedIndex);
            }
        });
        builder.setPositiveButton(android.R.string.cancel, null);
        builder.create().show();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener<T> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener<T> {
        @SuppressWarnings("UnusedParameters")
        void onItemSelectedListener(T item, int selectedIndex);
    }

}
