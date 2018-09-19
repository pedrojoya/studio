package es.iessaladillo.pedrojoya.pr014.ui.main;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;

import com.lucasurbas.listitemview.ListItemView;

import java.util.List;

import es.iessaladillo.pedrojoya.pr014.R;
import es.iessaladillo.pedrojoya.pr014.base.AdapterViewBaseAdapter;
import es.iessaladillo.pedrojoya.pr014.data.local.model.Student;

@SuppressWarnings("WeakerAccess")
public class MainActivityAdapter extends AdapterViewBaseAdapter<Student, MainActivityAdapter.ViewHolder> {

    interface Callback {
        void onDelete(MenuItem item, Student student, int position);
    }

    @SuppressWarnings("CanBeFinal")
    private final Callback listener;

    public MainActivityAdapter(@NonNull List<Student> data, Callback listener) {
        super(data, R.layout.activity_main_item);
        this.listener = listener;
    }

    @Override
    protected ViewHolder onCreateViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getItem(position), position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    class ViewHolder {

        public static final int ADULT_AGE = 18;

        final ListItemView listItemView;

        ViewHolder(final View itemView) {
            listItemView = (ListItemView) itemView;
        }

        void bind(final Student student, int position) {
            listItemView.setTitle(student.getName());
            listItemView.setSubtitle(student.getGrade() + " " + student.getLevel());
            listItemView.setIconColor(student.getAge() < ADULT_AGE ? ContextCompat.getColor(
                    listItemView.getContext(), R.color.accent) : ContextCompat.getColor(
                    listItemView.getContext(), R.color.primary));
            listItemView.setOnMenuItemClickListener(item -> listener.onDelete(item, student, position));
        }

    }

}
