package es.iessaladillo.pedrojoya.pr095.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import es.iessaladillo.pedrojoya.pr095.R;
import es.iessaladillo.pedrojoya.pr095.data.model.Song;

class MainFragmentAdapter extends ArrayAdapter<Song> {

    private final ListView listView;

    private final List<Song> songs;
    private final LayoutInflater layoutInflater;

    public MainFragmentAdapter(Context context, List<Song> songs, ListView listView) {
        super(context, R.layout.fragment_main_item, songs);
        this.songs = songs;
        layoutInflater = LayoutInflater.from(context);
        this.listView = listView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.fragment_main_item, parent, false);
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

    private void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bind(songs.get(position), position);
    }

    class ViewHolder {

        private final TextView lblName;
        private final TextView lblDuration;
        private final TextView lblAuthor;
        private final ImageView imgPlaying;

        public ViewHolder(View itemView) {
            lblName = itemView.findViewById(R.id.lblName);
            lblDuration = itemView.findViewById(R.id.lblDuration);
            lblAuthor = itemView.findViewById(R.id.lblAuthor);
            imgPlaying = itemView.findViewById(R.id.imgPlaying);
        }

        public void bind(Song song, int position) {
            lblName.setText(song.getName());
            lblDuration.setText(song.getDuration());
            lblAuthor.setText(song.getAuthor());
            File songFile = song.getPublicFile();
            if (songFile != null) {
                imgPlaying.setImageResource(listView.isItemChecked(
                        position) ? R.drawable.ic_equalizer_black_24dp : R.drawable
                                                    .ic_play_circle_outline_black_24dp);

            } else {
                imgPlaying.setImageResource(R.drawable.ic_file_download_black_24dp);
            }
        }

    }
}
