package es.iessaladillo.pedrojoya.pr220.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import es.iessaladillo.pedrojoya.pr220.R;

@SuppressWarnings("unchecked")
public class ClickToSelectEditText extends AppCompatEditText {

    private final CharSequence mHint;
    private OnItemSelectedListener onItemSelectedListener;
    private ListAdapter mSpinnerAdapter;

    public ClickToSelectEditText(Context context) {
        super(context);
        mHint = getHint();
    }

    public ClickToSelectEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttributes(context, attrs);
        mHint = getHint();
    }

    public ClickToSelectEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupAttributes(context, attrs);
        mHint = getHint();
    }

    private void setupAttributes(Context context, AttributeSet attrs) {
        // Obtain a typed array of attributes
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable
                        .ClickToSelectEditText, 0,
                0);
        // Extract custom attributes into member variables
        try {
            int entriesResId = a.getResourceId(R.styleable.ClickToSelectEditText_entries, 0);
            if (entriesResId != 0) {
                setAdapter(ArrayAdapter.createFromResource(context,
                        entriesResId, android.R.layout.simple_list_item_1));
            }
        } finally {
            // TypedArray objects are shared and must be recycled.
            a.recycle();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setFocusable(true);
        setClickable(true);
        setInputType(InputType.TYPE_NULL);
        setKeyListener(null);
    }

    @SuppressWarnings("WeakerAccess")
    public void setAdapter(ListAdapter adapter) {
        mSpinnerAdapter = adapter;
        configureOnClickListener();
    }


    private void configureOnClickListener() {
        setOnClickListener(this::showDialog);
        OnFocusChangeListener focusListener = getOnFocusChangeListener();
        setOnFocusChangeListener((view, hasFocus) -> {
            if (focusListener != null) {
                focusListener.onFocusChange(view, hasFocus);
            }
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
            setText(mSpinnerAdapter.getItem(selectedIndex).toString());
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelectedListener(
                        mSpinnerAdapter.getItem(selectedIndex), selectedIndex);
            }
        });
        builder.setPositiveButton(android.R.string.cancel, null);
        builder.create().show();
    }

    @SuppressWarnings("unused")
    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener {
        @SuppressWarnings("UnusedParameters")
        void onItemSelectedListener(Object item, int selectedIndex);
    }

}
