package es.iessaladillo.pedrojoya.pr059.ui.main;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr059.R;

public class MainFragmentAdapter extends ListAdapter<String, MainFragmentAdapter.ViewHolder> {

    MainFragmentAdapter() {
        super(new DiffUtil.ItemCallback<String>() {
            @Override
            public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                return TextUtils.equals(oldItem, newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                return TextUtils.equals(oldItem, newItem);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView text1;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = ViewCompat.requireViewById(itemView, android.R.id.text1);
            itemView.setOnClickListener(v -> navigateToDetail(getItem(getAdapterPosition())));
        }

        void bind(String student) {
            text1.setText(student);
        }

        private void navigateToDetail(String student) {
            Context context = itemView.getContext();
            Toast.makeText(context, context.getString(R.string.main_student_clicked, student),
                Toast.LENGTH_SHORT).show();
        }

    }

}
