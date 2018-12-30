package es.iessaladillo.pedrojoya.pr086.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr086.R;
import es.iessaladillo.pedrojoya.pr086.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr086.utils.PicassoUtils;
import es.iessaladillo.pedrojoya.pr086.utils.PopupMenuUtils;

class MainFragmentAdapter extends ListAdapter<Student, MainFragmentAdapter.ViewHolder> {

    private final MainFragmentViewModel viewModel;

    MainFragmentAdapter(@NonNull MainFragmentViewModel viewModel) {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName()) && TextUtils.equals(
                    oldItem.getAddress(), newItem.getAddress()) && TextUtils.equals(
                    oldItem.getGrade(), newItem.getGrade())
                    && oldItem.isRepeater() == newItem.isRepeater();
            }
        });
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public Student getItem(int position) {
        return super.getItem(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgPhoto;
        private final TextView lblName;
        private final TextView lblGrade;
        private final TextView lblAge;
        private final TextView lblRepeater;
        private final Button btnAssignments;
        private final Button btnMarks;
        private final ImageView imgPopupMenu;

        ViewHolder(View itemView) {
            super(itemView);
            imgPhoto = ViewCompat.requireViewById(itemView, R.id.imgPhoto);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblGrade = ViewCompat.requireViewById(itemView, R.id.lblGrade);
            lblAge = ViewCompat.requireViewById(itemView, R.id.lblAge);
            lblRepeater = ViewCompat.requireViewById(itemView, R.id.lblRepeater);
            btnAssignments = ViewCompat.requireViewById(itemView, R.id.btnAssignments);
            btnMarks = ViewCompat.requireViewById(itemView, R.id.btnMarks);
            imgPopupMenu = ViewCompat.requireViewById(itemView, R.id.imgPopupMenu);

            itemView.setOnClickListener(v -> viewModel.showStudent(getItem(getAdapterPosition())));
        }

        void bind(Student student) {
            lblName.setText(student.getName());
            lblGrade.setText(student.getGrade());
            lblAge.setText(lblAge.getContext()
                .getResources()
                .getQuantityString(R.plurals.main_adapter_years, student.getAge(),
                    student.getAge()));
            PicassoUtils.loadUrl(imgPhoto, student.getPhoto(), R.drawable.placeholder);
            lblAge.setTextColor(student.getAge() < 18 ? ContextCompat.getColor(lblAge.getContext(),
                R.color.accent) : ContextCompat.getColor(lblAge.getContext(),
                R.color.primary_text));
            lblRepeater.setVisibility(student.isRepeater() ? View.VISIBLE : View.INVISIBLE);
            btnAssignments.setOnClickListener(v -> viewModel.showAssignments(getItem(getAdapterPosition())));
            btnMarks.setOnClickListener(v -> viewModel.showMarks(getItem(getAdapterPosition())));
            imgPopupMenu.setOnClickListener(this::showPopup);
        }

        private void showPopup(View v) {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.activity_main_item_popup);
            popupMenu.setOnMenuItemClickListener(this::onMenuItemClick);
            PopupMenuUtils.enableIcons(popupMenu);
            popupMenu.show();
        }

        private boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.mnuCall:
                    viewModel.call(getItem(getAdapterPosition()));
                    break;
                case R.id.mnuSendMessage:
                    viewModel.sendMessage(getItem(getAdapterPosition()));
                    break;
                default:
                    return false;
            }
            return true;
        }

    }

}
