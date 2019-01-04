package es.iessaladillo.pedrojoya.pr251.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import es.iessaladillo.pedrojoya.pr251.R;

@SuppressWarnings({"unchecked", "WeakerAccess"})
public class SpinnerEditText<T> extends AppCompatEditText {

    private final CharSequence mHint;
    private OnItemSelectedListener<T> onItemSelectedListener;
    private ListAdapter mSpinnerAdapter;

    public SpinnerEditText(Context context) {
        super(context);
        mHint = getHint();
    }

    public SpinnerEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttributes(context, attrs);
        mHint = getHint();
    }

    public SpinnerEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupAttributes(context, attrs);
        mHint = getHint();
    }

    private void setupAttributes(Context context, AttributeSet attrs) {
        // Obtain a typed array of attributes
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SpinnerEditText,
            0, 0);
        // Extract custom attributes into member variables
        try {
            int entriesResId = a.getResourceId(R.styleable.SpinnerEditText_entries, 0);
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
            T item = (T) mSpinnerAdapter.getItem(selectedIndex);
            setText(item.toString());
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelectedListener(item, selectedIndex);
            }
        });
        builder.setPositiveButton(android.R.string.cancel, null);
        builder.create().show();
    }

    @SuppressWarnings("unused")
    public void setOnItemSelectedListener(OnItemSelectedListener<T> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener<T> {
        @SuppressWarnings("UnusedParameters")
        void onItemSelectedListener(T item, int selectedIndex);
    }

}
