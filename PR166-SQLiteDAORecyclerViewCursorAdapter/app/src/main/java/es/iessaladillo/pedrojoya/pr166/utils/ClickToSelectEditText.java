package es.iessaladillo.pedrojoya.pr166.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;

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
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }
        });
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showDialog(view);
                }
            }
        });
    }

    public void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(mHint);
        builder.setAdapter(mSpinnerAdapter, new DialogInterface.OnClickListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelectedListener((T) mSpinnerAdapter.getItem(selectedIndex), selectedIndex);
                }
            }
        });
        builder.setPositiveButton(android.R.string.cancel, null);
        builder.create().show();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener<T> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @SuppressWarnings("UnusedParameters")
    public interface OnItemSelectedListener<T> {
        void onItemSelectedListener(T item, int selectedIndex);
    }

}