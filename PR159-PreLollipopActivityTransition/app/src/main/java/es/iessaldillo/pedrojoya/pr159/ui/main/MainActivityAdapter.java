package es.iessaldillo.pedrojoya.pr159.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import es.iessaldillo.pedrojoya.pr159.R;
import es.iessaldillo.pedrojoya.pr159.data.model.Word;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    @SuppressWarnings("UnusedParameters")
    public interface OnItemClickListener {
        void onItemClick(View view, Word word, int position);
    }

    private OnItemClickListener onItemClickListener;
    @SuppressWarnings("CanBeFinal")
    private List<Word> mData;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_main_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, mData.get(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public MainActivityAdapter(List<Word> words) {
        mData = words;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgFoto;
        private final TextView lblEnglish;
        private final TextView lblSpanish;

        public ViewHolder(View itemView) {
            super(itemView);
            imgFoto = itemView.findViewById(R.id.imgPhoto);
            lblEnglish = itemView.findViewById(R.id.lblEnglish);
            lblSpanish = itemView.findViewById(R.id.lblSpanish);
        }

        public void bind(Word wor) {
            imgFoto.setImageResource(wor.getPhotoResId());
            lblEnglish.setText(wor.getEnglish());
            lblSpanish.setText(wor.getSpanish());
        }

    }

}
