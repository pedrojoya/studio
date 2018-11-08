package pedrojoya.iessaladillo.es.pr229.base;

import android.view.View;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

// V is ViewModel type, M is Model type.
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseListAdapter<M, V extends BaseViewHolder> extends ListAdapter<M, V> {

    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;

    public BaseListAdapter(DiffUtil.ItemCallback<M> diffUtilItemCallback) {
        super(diffUtilItemCallback);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    @Override
    public M getItem(int position) {
        return super.getItem(position);
    }

    public interface OnItemClickListener {
        @SuppressWarnings("unused")
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        @SuppressWarnings({"SameReturnValue", "unused"})
        boolean onItemLongClick(View view, int position);
    }

}
