package es.iessaladillo.pedrojoya.pr086.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import es.iessaladillo.pedrojoya.pr086.R;
import es.iessaladillo.pedrojoya.pr086.base.BaseListAdapter;
import es.iessaladillo.pedrojoya.pr086.base.BaseViewHolder;
import es.iessaladillo.pedrojoya.pr086.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr086.utils.PicassoUtils;
import es.iessaladillo.pedrojoya.pr086.utils.PopupMenuUtils;

class MainFragmentAdapter extends BaseListAdapter<Student, MainFragmentAdapter.ViewHolder> {

    private OnShowAssignmentsListener onShowAssignmentsListener;
    private OnShowMarksListener onShowMarksListener;
    private OnCallListener onCallListener;
    private OnSendMessageListener onSendMessageListener;

    MainFragmentAdapter() {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName()) && TextUtils.equals(
                    oldItem.getAddress(), newItem.getAddress()) && TextUtils.equals(
                    oldItem.getGrade(), newItem.getGrade())
                    && oldItem.isRepeater() == newItem.isRepeater();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    void setOnShowAssignmentsListener(OnShowAssignmentsListener onShowAssignmentsListener) {
        this.onShowAssignmentsListener = onShowAssignmentsListener;
    }

    void setOnShowMarksListener(OnShowMarksListener onShowMarksListener) {
        this.onShowMarksListener = onShowMarksListener;
    }

    public void setOnCallListener(OnCallListener onCallListener) {
        this.onCallListener = onCallListener;
    }

    public void setOnSendMessageListener(OnSendMessageListener onSendMessageListener) {
        this.onSendMessageListener = onSendMessageListener;
    }

    interface OnShowAssignmentsListener {
        void onCall(int position);
    }

    interface OnShowMarksListener {
        void onShowMarks(int position);
    }

    interface OnCallListener {
        void onCall(int position);
    }

    interface OnSendMessageListener {
        void onSendMessage(int position);
    }

    class ViewHolder extends BaseViewHolder {

        private final ImageView imgPhoto;
        private final TextView lblName;
        private final TextView lblGrade;
        private final TextView lblAge;
        private final TextView lblRepeater;
        private final Button btnCall;
        private final Button btnMarks;
        private final ImageView imgPopupMenu;

        ViewHolder(View itemView) {
            super(itemView, onItemClickListener);
            imgPhoto = ViewCompat.requireViewById(itemView, R.id.imgPhoto);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblGrade = ViewCompat.requireViewById(itemView, R.id.lblGrade);
            lblAge = ViewCompat.requireViewById(itemView, R.id.lblAge);
            lblRepeater = ViewCompat.requireViewById(itemView, R.id.lblRepeater);
            btnCall = ViewCompat.requireViewById(itemView, R.id.btnAssignments);
            btnMarks = ViewCompat.requireViewById(itemView, R.id.btnMarks);
            imgPopupMenu = ViewCompat.requireViewById(itemView, R.id.imgPopupMenu);
        }

        void bind(Student student) {
            lblName.setText(student.getName());
            lblGrade.setText(student.getGrade());
            lblAge.setText(lblAge.getContext()
                .getResources()
                .getQuantityString(R.plurals.main_adapter_years, student.getAge(),
                    student.getAge()));
            PicassoUtils.loadUrl(imgPhoto, student.getPhoto(), R.drawable.placeholder);
            lblAge.setTextColor(student.getAge() < 18 ? ContextCompat.getColor(lblAge.getContext(),
                R.color.accent) : ContextCompat.getColor(lblAge.getContext(),
                R.color.primary_text));
            lblRepeater.setVisibility(student.isRepeater() ? View.VISIBLE : View.INVISIBLE);
            btnCall.setOnClickListener(v -> {
                if (onShowAssignmentsListener != null) {
                    onShowAssignmentsListener.onCall(getAdapterPosition());
                }
            });
            btnMarks.setOnClickListener(v -> {
                if (onShowMarksListener != null) {
                    onShowMarksListener.onShowMarks(getAdapterPosition());
                }
            });
            imgPopupMenu.setOnClickListener(v -> showPopup(getAdapterPosition(), v));
        }

        private boolean onMenuItemClick(int position, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.mnuCall:
                    onCallListener.onCall(position);
                    break;
                case R.id.mnuSendMessage:
                    onSendMessageListener.onSendMessage(position);
                    break;
                default:
                    return false;
            }
            return true;
        }

        private void showPopup(int position, View v) {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.activity_main_item_popup);
            popupMenu.setOnMenuItemClickListener(menuItem -> onMenuItemClick(position, menuItem));
            PopupMenuUtils.enableIcons(popupMenu);
            popupMenu.show();
        }

    }

}
