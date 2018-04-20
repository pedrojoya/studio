package es.iessaladillo.pedrojoya.pr194.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.iessaladillo.pedrojoya.pr194.R;
import es.iessaladillo.pedrojoya.pr194.data.model.Student;

class MainActivityAdapter extends ArrayAdapter<Student> {

    private List<Student> students;
    private final LayoutInflater layoutInflater;

    @SuppressWarnings("SameParameterValue")
    public MainActivityAdapter(Context contexto, List<Student> students) {
        super(contexto, R.layout.activity_main_item, students);
        this.students = students;
        layoutInflater = LayoutInflater.from(contexto);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_main_item, parent, false);
            viewHolder = onCreateViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }

    private ViewHolder onCreateViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    private void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(students.get(position));
    }

    static class ViewHolder {

        private final ImageView imgPhoto;
        private final TextView lblName;
        private final TextView lblGrade;
        private final TextView lblAge;
        private final TextView lblRepeater;

        public ViewHolder(View itemView) {
            imgPhoto = ViewCompat.requireViewById(itemView, R.id.imgPhoto);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblGrade = ViewCompat.requireViewById(itemView, R.id.lblGrade);
            lblAge = ViewCompat.requireViewById(itemView, R.id.lblAge);
            lblRepeater = ViewCompat.requireViewById(itemView, R.id.lblRepeater);
        }

        public void bind(Student student) {
            lblName.setText(student.getName());
            lblGrade.setText(student.getGrade());
            lblAge.setText(lblAge.getContext()
                    .getResources()
                    .getQuantityString(R.plurals.main_activity_adapter_years, student.getAge(), student.getAge()));
            Picasso.with(imgPhoto.getContext()).load(student.getPhoto()).placeholder(
                    R.drawable.placeholder).error(R.drawable.placeholder).into(imgPhoto);
            lblAge.setTextColor(
                    student.getAge() < 18 ? ContextCompat.getColor(lblAge.getContext(),
                            R.color.accent) : ContextCompat.getColor(lblAge.getContext(),
                            R.color.primary_text));
            lblRepeater.setVisibility(student.isRepeater() ? View.VISIBLE : View.INVISIBLE);
        }

    }

    @Override
    public int getCount() {
        return students == null ? 0 : students.size();
    }

    public void setData(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

}

