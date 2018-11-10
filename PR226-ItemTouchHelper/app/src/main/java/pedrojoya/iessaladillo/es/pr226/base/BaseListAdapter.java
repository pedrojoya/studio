package pedrojoya.iessaladillo.es.pr226.base;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// V is ViewModel type, M is Model type.
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseListAdapter<M, V extends BaseViewHolder> extends RecyclerView.Adapter<V> {

    @NonNull
    protected List<M> data = new ArrayList<>();
    protected OnItemClickListener onItemClickListener;

    public void submitList(@NonNull List<M> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    public List<M> getList() {
        return data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public final int getItemCount() {
        return data.size();
    }

    public M getItem(int position) {
        return data.get(position);
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(@NonNull M item) {
        data.add(item);
        notifyItemInserted(data.size() - 1);
    }

    public void insertItem(@NonNull M item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull View view, int position);
    }

}