package es.iessaladillo.pedrojoya.pr017.main;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import es.iessaladillo.pedrojoya.pr017.R;
import es.iessaladillo.pedrojoya.pr017.base.AdapterViewBaseAdapter;
import es.iessaladillo.pedrojoya.pr017.data.local.model.Word;

class MainActivityAdapter extends AdapterViewBaseAdapter<Word, MainActivityAdapter.ViewHolder> implements Filterable {

    MainActivityAdapter(@NonNull List<Word> data) {
        super(data, R.layout.activity_main_item);
        setFilterPredicate((word, constraint) -> word
                .getEnglish()
                .toLowerCase()
                .contains(constraint.toString().toLowerCase()));
    }

    @Override
    protected ViewHolder onCreateViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    static class ViewHolder {

        private final ImageView imgFoto;
        private final TextView lblEnglish;

        ViewHolder(View itemView) {
            imgFoto = ViewCompat.requireViewById(itemView, R.id.imgPhoto);
            lblEnglish = ViewCompat.requireViewById(itemView, R.id.lblEnglish);
        }

        void bind(Word Word) {
            imgFoto.setImageResource(Word.getPhotoResId());
            lblEnglish.setText(Word.getEnglish());
        }

    }

}