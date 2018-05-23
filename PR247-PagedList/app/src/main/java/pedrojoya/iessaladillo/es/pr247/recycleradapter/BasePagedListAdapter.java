package pedrojoya.iessaladillo.es.pr247.recycleradapter;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.view.View;

// M is Model type, VH is ViewHolder type,
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BasePagedListAdapter<M, VH extends BaseViewHolder<M>> extends
        PagedListAdapter<M, VH> {

    private OnItemClickListener<M> onItemClickListener;
    private OnItemLongClickListener<M> onItemLongClickListener;
    private View emptyView;

    protected BasePagedListAdapter(@NonNull DiffUtil.ItemCallback<M> diffCallback) {
        super(diffCallback);
    }

    @Override
    public void submitList(PagedList<M> pagedList) {
        checkEmptyViewVisibility(pagedList.size());
        super.submitList(pagedList);
    }

    @Nullable
    @Override
    public M getItem(int position) {
        return super.getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener<M> listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<M> listener) {
        this.onItemLongClickListener = listener;
    }

    public void setEmptyView(@NonNull View emptyView) {
        this.emptyView = emptyView;
        checkEmptyViewVisibility(getCurrentList().size());
    }

    private void checkEmptyViewVisibility(int size) {
        if (emptyView != null) {
            emptyView.setVisibility(size <= 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @CallSuper
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(getItem(position));
    }

    public OnItemClickListener<M> getOnItemClickListener() {
        return onItemClickListener;
    }

    public OnItemLongClickListener<M> getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public View getEmptyView() {
        return emptyView;
    }
}
