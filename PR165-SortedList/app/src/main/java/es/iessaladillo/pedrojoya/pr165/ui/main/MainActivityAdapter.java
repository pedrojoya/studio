package es.iessaladillo.pedrojoya.pr165.ui.main;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import es.iessaladillo.pedrojoya.pr165.R;
import es.iessaladillo.pedrojoya.pr165.data.model.Product;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    @SuppressWarnings("UnusedParameters")
    public interface OnItemClickListener {
        void onItemClick(View view, Product product, int position);
    }

    @SuppressWarnings("UnusedParameters")
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, Product product, int position);
    }

    private final SortedList<Product> mData;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public MainActivityAdapter(List<Product> data) {
        // Create SortedList and listener for changes on data.
        mData = new SortedList<>(Product.class, new SortedList.Callback<Product>() {

            @Override
            public int compare(Product item1, Product item2) {
                // Alphabetically not case-sensitive.
                return item1.getName().toUpperCase().compareTo(item2.getName().toUpperCase());
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            // Similar contents?
            @Override
            public boolean areContentsTheSame(Product oldItem, Product newItem) {
                return oldItem.getName().toUpperCase().equals(newItem.getName().toUpperCase())
                        && oldItem.getQuantity() == newItem.getQuantity() && oldItem.getUnit()
                        .toUpperCase()
                        .equals(newItem.getUnit().toUpperCase());
            }

            // Same product? Update if same product with different content.
            @Override
            public boolean areItemsTheSame(Product item1, Product item2) {
                return item1.getName().toUpperCase().equals(item2.getName().toUpperCase());
            }

        });
        if (data != null) {
            mData.addAll(data);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_main_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, mData.get(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(v -> {
            if (mOnItemLongClickListener != null) {
                mOnItemLongClickListener.onItemLongClick(v,
                        mData.get(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
                return true;
            } else {
                return false;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainActivityAdapter.ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    public int getItemCount() {
        return mData.size();
    }

    @SuppressWarnings("UnusedReturnValue")
    public int addItem(Product product) {
        return mData.add(product);
    }

    public void removeItem(Product product) {
        mData.remove(product);
    }

    public void toggleBought(int position) {
        notifyItemChanged(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblName;
        private final TextView lblUnits;
        private final View vLine;

        public ViewHolder(View itemView) {
            super(itemView);
            lblName = itemView.findViewById(R.id.lblName);
            lblUnits = itemView.findViewById(R.id.lblUnits);
            vLine = itemView.findViewById(R.id.vLine);
        }

        public void bind(Product product) {
            lblName.setText(product.getName());
            String texto;
            int value = (int) product.getQuantity();
            if (value == product.getQuantity()) {
                texto = itemView.getContext().getString(R.string
                                .main_activity_data_units, value,
                        product.getUnit());
            } else {
                texto = itemView.getContext().getString(R.string.main_activity_data_units,
                        product.getQuantity(), product.getUnit());
            }
            lblUnits.setText(texto);
            vLine.setVisibility(product.isBought() ? View.VISIBLE : View.INVISIBLE);
        }
    }

}
