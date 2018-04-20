package es.iessaladillo.pedrojoya.pr016;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnChildClickListener {

    private StudentsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        ExpandableListView lstStudents = ActivityCompat.requireViewById(this, R.id.lstStudents);
        // We won't use default indicators for group or child.
        lstStudents.setGroupIndicator(null);
        lstStudents.setChildIndicator(null);
        ArrayList<String> groups = new ArrayList<>();
        ArrayList<ArrayList<Student>> children = new ArrayList<>();
        fillData(groups, children);
        adapter = new StudentsAdapter(groups, children);
        lstStudents.setAdapter(adapter);
        // All groups initially expanded.
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            lstStudents.expandGroup(i);
        }
        lstStudents.setOnChildClickListener(this);
    }

    private void fillData(ArrayList<String> grupos, ArrayList<ArrayList<Student>> children) {
        ArrayList<Student> group;

        grupos.add("CFGM Sistemas Microinformáticos y Redes");
        group = new ArrayList<>();
        group.add(new Student("Baldomero", 16, "CFGM", "2º"));
        group.add(new Student("Sergio", 27, "CFGM", "1º"));
        group.add(new Student("Atanasio", 17, "CFGM", "1º"));
        group.add(new Student("Oswaldo", 26, "CFGM", "1º"));
        group.add(new Student("Rodrigo", 22, "CFGM", "2º"));
        group.add(new Student("Antonio", 16, "CFGM", "1º"));
        children.add(group);

        grupos.add("CFGS Desarrollo de Aplicaciones Multiplataforma");
        group = new ArrayList<>();
        group.add(new Student("Pedro", 22, "CFGS", "2º"));
        group.add(new Student("Pablo", 22, "CFGS", "2º"));
        group.add(new Student("Rodolfo", 21, "CFGS", "1º"));
        group.add(new Student("Gervasio", 24, "CFGS", "2º"));
        group.add(new Student("Prudencia", 20, "CFGS", "2º"));
        group.add(new Student("Gumersindo", 17, "CFGS", "2º"));
        group.add(new Student("Gerardo", 18, "CFGS", "1º"));
        group.add(new Student("Óscar", 21, "CFGS", "2º"));
        children.add(group);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
            int childPosition, long id) {
        // Use getExpandableListAdapter() instead of getAdapter() in case you need the adapter.
        Student student = adapter.getChild(groupPosition, childPosition);
        Toast.makeText(this,
                getString(R.string.main_activity_student_info, student.getName(), student.getGrade(),
                        student.getLevel()), Toast.LENGTH_SHORT).show();
        return true;
    }

    private static class StudentsAdapter extends BaseExpandableListAdapter {

        private final ArrayList<String> groups;
        private final ArrayList<ArrayList<Student>> children;

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

        StudentsAdapter(ArrayList<String> groups, ArrayList<ArrayList<Student>> children) {
            this.groups = groups;
            this.children = children;
        }

        @Override
        public Student getChild(int groupPosition, int childPosition) {
            return children.get(groupPosition).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            // We don't manage ids.
            return 0;
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
        public ArrayList<Student> getGroup(int groupPosition) {
            return children.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return children.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            // We won't manage ids.
            return 0;
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

}
