package es.iessaladillo.pedrojoya.pr016.ui.main;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import es.iessaladillo.pedrojoya.pr016.R;
import es.iessaladillo.pedrojoya.pr016.data.local.model.Student;

class MainActivityAdapter extends BaseExpandableListAdapter {

    private final List<String> groups;
    private final List<List<Student>> children;

    private static class ChildViewHolder {

        private static final int ADULT_AGE = 18;

        private final TextView lblName;
        private final TextView lblGrade;

        ChildViewHolder(View itemView) {
            lblName = ViewCompat.requireViewById(itemView, R.id.lblNombre);
            lblGrade = ViewCompat.requireViewById(itemView, R.id.lblCurso);
            itemView.setTag(this);
        }

        void bind(Student student) {
            lblName.setText(student.getName());
            lblGrade.setText(student.getGrade());
            lblName.setTextColor(
                    student.getAge() < ADULT_AGE ? ContextCompat.getColor(lblName.getContext(),
                            R.color.primary_400) : ContextCompat.getColor(lblName.getContext(),
                            R.color.primary_text));
        }
    }

    private static class GroupViewHolder {

        private final TextView lblLevelHeader;
        private final ImageView imgIndicator;
        private final LinearLayout llColumnsHeader;

        GroupViewHolder(View itemView) {
            lblLevelHeader = ViewCompat.requireViewById(itemView, R.id.lblLevelHeader);
            imgIndicator = ViewCompat.requireViewById(itemView, R.id.imgIndicator);
            llColumnsHeader = ViewCompat.requireViewById(itemView, R.id.llColumnsHeader);
            itemView.setTag(this);
        }

        void bind(String group, int childrenCount, boolean isExpanded) {
            lblLevelHeader.setText(group);
            // Show or hide columns header and change indicator.
            if (childrenCount == 0) {
                imgIndicator.setVisibility(View.INVISIBLE);
                llColumnsHeader.setVisibility(View.GONE);
            } else {
                imgIndicator.setVisibility(View.VISIBLE);
                if (isExpanded) {
                    imgIndicator.setImageResource(R.drawable.ic_expand_less_white_24dp);
                    llColumnsHeader.setVisibility(View.VISIBLE);
                } else {
                    imgIndicator.setImageResource(R.drawable.ic_expand_more_white_24dp);
                    llColumnsHeader.setVisibility(View.GONE);
                }
            }
        }

    }

    MainActivityAdapter(List<String> groups, List<List<Student>> children) {
        this.groups = groups;
        this.children = children;
    }

    @Override
    public Student getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getChild(groupPosition, childPosition).getId();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.activity_main_child, parent, false);
            childViewHolder = new ChildViewHolder(convertView);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.bind(children.get(groupPosition).get(childPosition));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(groupPosition).size();
    }

    @Override
    public List<Student> getGroup(int groupPosition) {
        return children.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return children.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.activity_main_group, parent, false);
            groupViewHolder = new GroupViewHolder(convertView);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.bind(groups.get(groupPosition), getChildrenCount(groupPosition), isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // Default behavior
        return false;
    }

    @Override
    public boolean isChildSelectable(int posGrupo, int posHijo) {
        // Default behavior
        return true;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        // Expand group if you want to be always expanded
        // lstStudents.expandGroup(groupPosition);
    }

}
