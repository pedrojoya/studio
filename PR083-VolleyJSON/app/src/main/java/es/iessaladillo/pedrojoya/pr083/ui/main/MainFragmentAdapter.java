package es.iessaladillo.pedrojoya.pr083.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr083.R;
import es.iessaladillo.pedrojoya.pr083.data.model.Student;

class MainFragmentAdapter extends ListAdapter<Student, MainFragmentAdapter.ViewHolder> {

    MainFragmentAdapter() {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName()) && TextUtils.equals(
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

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgPhoto;
        private final TextView lblName;
        private final TextView lblGrade;
        private final TextView lblAge;
        private final TextView lblRepeater;

        ViewHolder(View itemView) {
            super(itemView);
            imgPhoto = ViewCompat.requireViewById(itemView, R.id.imgPhoto);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblGrade = ViewCompat.requireViewById(itemView, R.id.lblGrade);
            lblAge = ViewCompat.requireViewById(itemView, R.id.lblAge);
            lblRepeater = ViewCompat.requireViewById(itemView, R.id.lblRepeater);
        }

        void bind(Student student) {
            lblName.setText(student.getName());
            lblGrade.setText(student.getGrade());
            lblAge.setText(lblAge.getContext()
                .getResources()
                .getQuantityString(R.plurals.main_item_years, student.getAge(), student.getAge()));
            Picasso.with(imgPhoto.getContext()).load(student.getPhoto()).placeholder(
                R.drawable.placeholder).error(R.drawable.placeholder).into(imgPhoto);
            lblAge.setTextColor(student.getAge() < 18 ? ContextCompat.getColor(lblAge.getContext(),
                R.color.accent) : ContextCompat.getColor(lblAge.getContext(),
                R.color.primary_text));
            lblRepeater.setVisibility(student.isRepeater() ? View.VISIBLE : View.INVISIBLE);
        }

    }

}

