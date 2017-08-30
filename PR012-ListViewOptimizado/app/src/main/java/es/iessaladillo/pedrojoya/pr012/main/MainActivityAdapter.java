package es.iessaladillo.pedrojoya.pr012.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr012.R;
import es.iessaladillo.pedrojoya.pr012.data.model.Student;

class MainActivityAdapter extends BaseAdapter {

    private final List<Student> mData;
    private final LayoutInflater mLayoutInflater;

    public MainActivityAdapter(Context context, @NonNull ArrayList<Student> data) {
        mData = data;
        mLayoutInflater = LayoutInflater.from(context);
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

    private void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    static class ViewHolder {
        private final ImageView imgPhoto;
        private final TextView lblName;
        private final TextView lblGrade;
        private final TextView lblAge;
        private final TextView lblRepeater;
        private final Button btnCall;
        private final Button btnMarks;

        public ViewHolder(View itemView) {
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            lblName = itemView.findViewById(R.id.lblName);
            lblGrade = itemView.findViewById(R.id.lblGrade);
            lblAge = itemView.findViewById(R.id.lblAge);
            lblRepeater = itemView.findViewById(R.id.lblRepeater);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnMarks = itemView.findViewById(R.id.btnMarks);
        }

        public void bind(final Student student) {
            imgPhoto.setImageResource(student.getPhoto());
            lblName.setText(student.getName());
            lblGrade.setText(lblGrade.getContext()
                    .getString(R.string.main_activity_grade_and_level, student.getGrade(), student.getLevel()));
            lblAge.setText(lblAge.getContext()
                    .getResources()
                    .getQuantityString(R.plurals.main_activity_age, student.getAge(), student.getAge()));
            lblAge.setTextColor(student.getAge() < 18 ?
                    ContextCompat.getColor(lblAge.getContext(), R.color.accent) :
                    ContextCompat.getColor(lblAge.getContext(), R.color.primary_text));
            lblRepeater.setVisibility(student.isRepeater() ? View.VISIBLE : View.INVISIBLE);
            btnCall.setOnClickListener(view ->
                    Toast.makeText(view.getContext(),
                    view.getContext().getString(R.string.main_activity_call_sb, student.getName()),
                    Toast.LENGTH_SHORT).show());
            btnMarks.setOnClickListener(view ->
                    Toast.makeText(view.getContext(),
                    view.getContext().getString(R.string.main_activity_show_sb_marks, student.getName()),
                    Toast.LENGTH_SHORT).show());
        }

    }

}
