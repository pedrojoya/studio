package es.iessaladillo.pedrojoya.pr045.ui.main.v2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr045.R;

class MainFragmentAdapterV2 extends ListAdapter<Integer, MainFragmentAdapterV2.ViewHolder> {

    MainFragmentAdapterV2() {
        super(new DiffUtil.ItemCallback<Integer>() {
            @Override
            public boolean areItemsTheSame(@NonNull Integer oldItem, @NonNull Integer newItem) {
                return Objects.equals(oldItem, newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Integer oldItem, @NonNull Integer newItem) {
                return true;
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_main_page,
            parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblPage;
        private final Button btnShow;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblPage = ViewCompat.requireViewById(itemView, R.id.lblPage);
            btnShow = ViewCompat.requireViewById(itemView, R.id.btnShow);
            btnShow.setOnClickListener(v -> show(v, getAdapterPosition()));
        }

        void bind(Integer page) {
            lblPage.setText(String.valueOf(page));
        }

        private void show(View view, int position) {
            Snackbar.make(view, view.getContext().getString(R.string.main_message, position),
                Snackbar.LENGTH_SHORT).show();
        }

    }

}
