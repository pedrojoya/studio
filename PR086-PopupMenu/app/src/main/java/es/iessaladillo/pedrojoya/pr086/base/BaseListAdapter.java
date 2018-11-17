package es.iessaladillo.pedrojoya.pr086.base;

import android.view.View;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

// V is ViewModel type, M is Model type.
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseListAdapter<M, V extends BaseViewHolder> extends ListAdapter<M, V> {

    protected OnItemClickListener onItemClickListener;

    public BaseListAdapter(DiffUtil.ItemCallback<M> diffUtilItemCallback) {
        super(diffUtilItemCallback);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    @Override
    public M getItem(int position) {
        return super.getItem(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
