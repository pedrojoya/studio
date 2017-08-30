package es.iessaladillo.pedrojoya.pr015.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr015.R;
import es.iessaladillo.pedrojoya.pr015.data.model.Word;

class MainActivityAdapter extends BaseAdapter {

    private final ArrayList<Word> mData;
    private final LayoutInflater mLayoutInflater;

    public MainActivityAdapter(Context context, @NonNull ArrayList<Word> data) {
        this.mData = data;
        mLayoutInflater = LayoutInflater.from(context);
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
        // No ids managed.
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_main_item, parent, false);
            holder = onCreateViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    @NonNull
    private ViewHolder onCreateViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    private void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    static class ViewHolder {

        private final ImageView imgFoto;
        private final TextView lblEnglish;
        private final TextView lblSpanish;

        public ViewHolder(View itemView) {
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