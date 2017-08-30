package es.iessaladillo.pedrojoya.pr014.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lucasurbas.listitemview.ListItemView;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr014.Constants;
import es.iessaladillo.pedrojoya.pr014.R;
import es.iessaladillo.pedrojoya.pr014.data.model.Student;

@SuppressWarnings("WeakerAccess")
public class MainActivityAdapter extends BaseAdapter {

    interface Callback {
        void onDelete(MenuItem item, Student student, int position);
    }

    @SuppressWarnings("CanBeFinal")
    private ArrayList<Student> mData;
    private final Callback mListener;
    private final LayoutInflater mInflater;

    public MainActivityAdapter(Context context, @NonNull ArrayList<Student> data, Callback listener) {
        mData = data;
        mListener = listener;
        mInflater = LayoutInflater.from(context);
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
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_main_item, parent, false);
            viewHolder = onCreateViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }

    @NonNull
    private ViewHolder onCreateViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    private void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bind(mData.get(position), position);
    }

    private class ViewHolder {

        final ListItemView listItemView;

        ViewHolder(final View itemView) {
            listItemView = (ListItemView) itemView;
        }

        void bind(final Student student, int position) {
            listItemView.setTitle(student.getName());
            listItemView.setSubtitle(student.getGrade() + " " + student.getLevel());
            listItemView.setIconColor(student.getAge() < Constants.ADULT_AGE ? ContextCompat.getColor(
                    listItemView.getContext(), R.color.accent) : ContextCompat.getColor(
                    listItemView.getContext(), R.color.primary));
            listItemView.setOnMenuItemClickListener(item -> mListener.onDelete(item, student, position));
        }

    }

}
