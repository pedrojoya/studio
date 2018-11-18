package es.iessaladillo.pedrojoya.pr059.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr059.data.Repository;

@SuppressWarnings("WeakerAccess")
public class MainFragmentViewModel extends ViewModel {

    private final MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private final LiveData<List<String>> students;
    private boolean searchViewExpanded;

    public MainFragmentViewModel(Repository repository) {
        students = Transformations.switchMap(searchQuery, repository::queryStudents);
        searchQuery.postValue("");
    }

    public String getSearchQuery() {
        return searchQuery.getValue();
    }

    public void setSearchQuery(String criteria) {
        searchQuery.postValue(criteria);
    }

    public boolean isSearchViewExpanded() {
        return searchViewExpanded;
    }

    public void setSearchViewExpanded(boolean searchViewExpanded) {
        this.searchViewExpanded = searchViewExpanded;
    }

    public LiveData<List<String>> getStudents() {
        return students;
    }

}
