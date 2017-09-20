package es.iessaladillo.pedrojoya.pr179;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, Word word, int position);
    }

    private OnItemClickListener onItemClickListener;
    private final List<Word> mWords;

    public MainActivityAdapter(ArrayList<Word> words) {
        mWords = words;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, mWords.get(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mWords.get(position));
    }

    @Override
    public int getItemCount() {
        return mWords == null ? 0 : mWords.size();
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

        public void bind(Word word) {
            imgFoto.setImageResource(word.getPhotoResId());
            lblEnglish.setText(word.getEnglish());
            lblSpanish.setText(word.getSpanish());
        }

    }

}
