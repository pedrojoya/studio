package es.iessaladillo.pedrojoya.pr251.ui.list;


import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import es.iessaladillo.pedrojoya.pr251.R;
import es.iessaladillo.pedrojoya.pr251.base.BaseListAdapter;
import es.iessaladillo.pedrojoya.pr251.base.BaseViewHolder;
import es.iessaladillo.pedrojoya.pr251.data.local.model.Student;

public class ListFragmentAdapter extends BaseListAdapter<Student, ListFragmentAdapter.ViewHolder> {

    private final TextDrawable.IBuilder drawableBuilder;
    private static final DiffUtil.ItemCallback<Student> diffUtilItemCallback = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(Student oldItem, Student newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Student oldItem, Student newItem) {
            return TextUtils.equals(oldItem.getName(), newItem.getName()) && TextUtils.equals(
                    oldItem.getAddress(), newItem.getAddress()) && TextUtils.equals(
                    oldItem.getGrade(), newItem.getGrade());
        }
    };


    ListFragmentAdapter() {
        super(diffUtilItemCallback);
        drawableBuilder = TextDrawable.builder()
                .beginConfig()
                .width(100)
                .height(100)
                .toUpperCase()
                .endConfig()
                .round();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListFragmentAdapter.ViewHolder viewHolder,
            int position) {
        viewHolder.bind(getItem(position));
    }

    class ViewHolder extends BaseViewHolder {
        private final TextView lblName;
        private final TextView lblAddress;
        private final ImageView imgAvatar;

        private final TextView lblGrade;

        ViewHolder(View itemView) {
            super(itemView, onItemClickListener, onItemLongClickListener);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblGrade = ViewCompat.requireViewById(itemView, R.id.lblGrade);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblAddress);
        }

        void bind(Student student) {
            lblName.setText(student.getName());
            lblGrade.setText(student.getGrade());
            lblAddress.setText(student.getAddress());
            imgAvatar.setImageDrawable(drawableBuilder.build(
                    itemView.isActivated() ? "\u2713" : student.getName().substring(0, 1),
                    itemView.isActivated() ? Color.GRAY : ColorGenerator.MATERIAL.getColor(
                            student.getName())));
        }

    }

}
