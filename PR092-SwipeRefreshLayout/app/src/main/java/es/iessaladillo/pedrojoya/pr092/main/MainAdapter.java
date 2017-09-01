package es.iessaladillo.pedrojoya.pr092.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private final List<String> mData;

    public MainAdapter(List<String> data) {
        mData = data;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
        holder.onBind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItem(String item) {
        mData.add(item);
        notifyItemInserted(mData.size() - 1);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView text1;

        public ViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
        }

        public void onBind(String elemento) {
            text1.setText(elemento);
        }

    }

}
