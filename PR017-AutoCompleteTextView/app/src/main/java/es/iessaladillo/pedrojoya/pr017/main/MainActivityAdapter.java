package es.iessaladillo.pedrojoya.pr017.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr017.R;
import es.iessaladillo.pedrojoya.pr017.data.local.model.Word;

class MainActivityAdapter extends BaseAdapter implements Filterable {

    private final ArrayList<Word> mOriginalWords;
    private ArrayList<Word> mData;
    private final LayoutInflater mLayoutInflater;

    public MainActivityAdapter(Context context, ArrayList<Word> data) {
        mLayoutInflater = LayoutInflater.from(context);
        mOriginalWords = data;
        mData = new ArrayList<>(data);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_main_item, parent, false);
            viewHolder = onCreateViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }

    private ViewHolder onCreateViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    private void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mData.get(position));
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

    @NonNull
    @Override
    public Filter getFilter() {
        return new MainActivityAdapterFilter();
    }

    class MainActivityAdapterFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null) {
                mData = (ArrayList<Word>) Stream.of(mOriginalWords).filter(
                        word -> word.getEnglish()
                                .toLowerCase()
                                .contains(constraint.toString().toLowerCase())).collect(
                        Collectors.toList());
                filterResults.values = mData;
                filterResults.count = mData.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            if (filterResults != null && filterResults.count > 0) {
                notifyDataSetChanged();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Word Word = (Word) resultValue;
            return Word.getEnglish();
        }

    }


}