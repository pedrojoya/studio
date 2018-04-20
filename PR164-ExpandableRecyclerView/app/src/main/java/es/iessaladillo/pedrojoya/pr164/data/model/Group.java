package es.iessaladillo.pedrojoya.pr164.data.model;

import android.view.View;

import java.util.List;

import es.iessaladillo.pedrojoya.pr164.R;
import es.iessaladillo.pedrojoya.pr164.multityperecycleradapter.Item;
import es.iessaladillo.pedrojoya.pr164.ui.main.GroupViewHolder;

public class Group extends Item<GroupViewHolder> {

    private int id;
    private String name;
    private List<Student> students;

    public Group(int id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public int getLayoutResIdForType() {
        return R.layout.activity_main_group;
    }

    @Override
    public GroupViewHolder createViewHolder(View itemView) {
        return new GroupViewHolder(itemView);
    }

    @Override
    public void bind(GroupViewHolder holder, int position) {
        holder.lblName.setText(getName());
        //        imgCollapse.setImageResource(group.getHiddenChildren()
        //                                              == null ? R.drawable.ic_arrow_drop_up : R.drawable.ic_arrow_drop_down);

    }

    @Override
    public boolean areItemsTheSame(Item newItem) {
        return getId() == ((Group) newItem).getId();
    }

    @Override
    public boolean areContentsTheSame(Item newItem) {
        return equals(newItem);
    }

}
