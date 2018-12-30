package es.iessaladillo.pedrojoya.pr249.ui.list;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr249.R;
import es.iessaladillo.pedrojoya.pr249.ui.detail.DetailFragment;

public class ListFragmentAdapter extends ListAdapter<String, ListFragmentAdapter.ViewHolder> {

    ListFragmentAdapter() {
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
    public ListFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
        int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListFragmentAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView text1;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = ViewCompat.requireViewById(itemView, android.R.id.text1);
            itemView.setOnClickListener(v -> navigateToDetail(getItem(getAdapterPosition())));
        }

        void bind(String item) {
            text1.setText(item);
        }

        private void navigateToDetail(String item) {
            FragmentManager fragmentManager = ((AppCompatActivity) (itemView.getContext()))
                .getSupportFragmentManager();
            fragmentManager.beginTransaction()
                .replace(R.id.flContent, DetailFragment.newInstance(item),
                    DetailFragment.class.getSimpleName())
                .addToBackStack(DetailFragment.class.getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
        }

    }

}
