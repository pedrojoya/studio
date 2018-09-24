package es.iessaladillo.pedrojoya.pr059.ui.main;

import java.util.List;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr059.data.Repository;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    private String searchQuery;
    private boolean searchViewExpanded;
    private List<String> students;
    private final Repository repository;

    public MainFragmentViewModel(Repository repository) {
        this.repository = repository;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public boolean isSearchViewExpanded() {
        return searchViewExpanded;
    }

    public void setSearchViewExpanded(boolean searchViewExpanded) {
        this.searchViewExpanded = searchViewExpanded;
    }

    public List<String> getStudents() {
        if (students == null) {
            students = repository.queryStudents();
        }
        return students;
    }
}
