package es.iessaladillo.pedrojoya.pr086.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import es.iessaladillo.pedrojoya.pr086.R;
import es.iessaladillo.pedrojoya.pr086.data.model.Student;
import es.iessaladillo.pedrojoya.pr086.utils.PopupMenuUtils;

class MainActivityAdapter extends BaseAdapter {

    // Communication interface
    interface Callback {

        void onCall(Student student);
        void onSendMessage(Student student);
    }

    private final List<Student> mData;
    private final Callback mListener;
    private final LayoutInflater mInflater;

    public MainActivityAdapter(Context context, List<Student> data, Callback listener) {
        mData = data;
        mInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_main_item, parent, false);
            holder = onCreateViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    private ViewHolder onCreateViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    private void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bind(mData.get(position));
    }

    private class ViewHolder {

        private final TextView lblName;
        private final TextView lblPhone;
        private final TextView lblAddress;
        private final TextView lblGrade;
        private final ImageView imgPopupMenu;

        ViewHolder(View itemView) {
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblPhone = ViewCompat.requireViewById(itemView, R.id.lblPhone);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblAddress);
            lblGrade = ViewCompat.requireViewById(itemView, R.id.lblGrade);
            imgPopupMenu = ViewCompat.requireViewById(itemView, R.id.imgPopupMenu);
        }

        private void showPopup(Student student, View v) {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.activity_main_item_popup);
            popupMenu.setOnMenuItemClickListener(menuItem -> onMenuItemClick(student, menuItem));
            PopupMenuUtils.enableIcons(popupMenu);
            popupMenu.show();
        }

        void bind(Student student) {
            lblName.setText(student.getName());
            lblPhone.setText(student.getPhone());
            lblAddress.setText(student.getAddress());
            lblGrade.setText(student.getGrade());
            imgPopupMenu.setOnClickListener(v -> showPopup(student, v));
        }

        private boolean onMenuItemClick(Student student, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.mnuCall:
                    mListener.onCall(student);
                    break;
                case R.id.mnuSendMessage:
                    mListener.onSendMessage(student);
                    break;
                default:
                    return false;
            }
            return true;
        }
    }

}
