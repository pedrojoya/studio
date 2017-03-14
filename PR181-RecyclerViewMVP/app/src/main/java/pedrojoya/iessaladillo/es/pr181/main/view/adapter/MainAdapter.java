package pedrojoya.iessaladillo.es.pr181.main.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pedrojoya.iessaladillo.es.pr181.R;
import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;
import pedrojoya.iessaladillo.es.pr181.main.view.holder.MainViewHolder;
import pedrojoya.iessaladillo.es.pr181.main.view.listener.OnMainItemClickListener;
import pedrojoya.iessaladillo.es.pr181.main.view.listener.OnMainItemLongClickListener;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private List<Student> mData;
    private OnMainItemLongClickListener onItemLongClickListener;
    private OnMainItemClickListener onItemClickListener;

    public MainAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_main_item, parent, false);
        final MainViewHolder viewHolder = new MainViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, mData.get(viewHolder.getAdapterPosition()),
                            viewHolder.getAdapterPosition());
                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(v,
                            mData.get(viewHolder.getAdapterPosition()),
                            viewHolder.getAdapterPosition());
                    return true;
                } else {
                    return false;
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void removeItem(int position) {
        //mData.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Student alumno) {
        //mData.add(alumno);
        notifyItemInserted(mData.size() - 1);
    }

    public void setOnItemClickListener(OnMainItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnMainItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public void setData(List<Student> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
