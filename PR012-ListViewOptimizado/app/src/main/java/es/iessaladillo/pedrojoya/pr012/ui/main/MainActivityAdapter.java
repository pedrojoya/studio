package es.iessaladillo.pedrojoya.pr012.ui.main;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import es.iessaladillo.pedrojoya.pr012.R;
import es.iessaladillo.pedrojoya.pr012.base.AdapterViewBaseAdapter;
import es.iessaladillo.pedrojoya.pr012.data.local.model.Student;

class MainActivityAdapter extends AdapterViewBaseAdapter<Student, MainActivityAdapter.ViewHolder> {

    private CallListener callListener;
    private ShowMarksListener showMarksListener;

    MainActivityAdapter(@NonNull List<Student> data) {
        super(data, R.layout.activity_main_item);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    void setCallListener(CallListener callListener) {
        this.callListener = callListener;
    }

    void setShowMarksListener(ShowMarksListener showMarksListener) {
        this.showMarksListener = showMarksListener;
    }

    interface CallListener {
        void onCall(View view, int position);
    }

    interface ShowMarksListener {
        void onShowMarks(View view, int position);
    }

    class ViewHolder {
        private final ImageView imgPhoto;
        private final TextView lblName;
        private final TextView lblGrade;
        private final TextView lblAge;
        private final TextView lblRepeater;
        private final Button btnCall;
        private final Button btnMarks;

        ViewHolder(View itemView) {
            imgPhoto = ViewCompat.requireViewById(itemView, R.id.imgPhoto);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblGrade = ViewCompat.requireViewById(itemView, R.id.lblGrade);
            lblAge = ViewCompat.requireViewById(itemView, R.id.lblAge);
            lblRepeater = ViewCompat.requireViewById(itemView, R.id.lblRepeater);
            btnCall = ViewCompat.requireViewById(itemView, R.id.btnCall);
            btnMarks = ViewCompat.requireViewById(itemView, R.id.btnMarks);
        }

        void bind(final Student student, int position) {
            imgPhoto.setImageResource(student.getPhoto());
            lblName.setText(student.getName());
            lblGrade.setText(lblGrade.getContext()
                    .getString(R.string.main_activity_grade_and_level, student.getGrade(),
                            student.getLevel()));
            lblAge.setText(lblAge.getContext()
                    .getResources()
                    .getQuantityString(R.plurals.main_activity_age, student.getAge(),
                            student.getAge()));
            lblAge.setTextColor(student.getAge() < 18 ? ContextCompat.getColor(lblAge.getContext(),
                    R.color.accent) : ContextCompat.getColor(lblAge.getContext(),
                    R.color.primary_text));
            lblRepeater.setVisibility(student.isRepeater() ? View.VISIBLE : View.INVISIBLE);
            btnCall.setOnClickListener(view -> {
                if (callListener != null) {
                    callListener.onCall(view, position);
                }
            });
            btnMarks.setOnClickListener(view -> {
                if (showMarksListener != null) {
                    showMarksListener.onShowMarks(view, position);
                }
            });
        }

    }

}
