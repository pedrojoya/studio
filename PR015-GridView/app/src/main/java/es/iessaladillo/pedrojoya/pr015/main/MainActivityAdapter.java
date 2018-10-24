package es.iessaladillo.pedrojoya.pr015.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import es.iessaladillo.pedrojoya.pr015.R;
import es.iessaladillo.pedrojoya.pr015.base.AdapterViewBaseAdapter;
import es.iessaladillo.pedrojoya.pr015.data.local.model.Word;

class MainActivityAdapter extends AdapterViewBaseAdapter<Word, MainActivityAdapter.ViewHolder> {

    MainActivityAdapter(@NonNull List<Word> data) {
        super(data, R.layout.activity_main_item);
    }

    @Override
    protected ViewHolder onCreateViewHolder(@NonNull View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    static class ViewHolder {

        private final ImageView imgFoto;
        private final TextView lblEnglish;
        private final TextView lblSpanish;

        ViewHolder(View itemView) {
            imgFoto = ViewCompat.requireViewById(itemView, R.id.imgPhoto);
            lblEnglish = ViewCompat.requireViewById(itemView, R.id.lblEnglish);
            lblSpanish = ViewCompat.requireViewById(itemView, R.id.lblSpanish);
        }

        void bind(Word word) {
            imgFoto.setImageResource(word.getPhotoResId());
            lblEnglish.setText(word.getEnglish());
            lblSpanish.setText(word.getSpanish());
        }

    }

}