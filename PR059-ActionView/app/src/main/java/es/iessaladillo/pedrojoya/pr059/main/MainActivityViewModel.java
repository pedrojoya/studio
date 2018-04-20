package es.iessaladillo.pedrojoya.pr059.main;

import android.arch.lifecycle.ViewModel;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends ViewModel {

    private String searchQuery;
    private boolean searchViewExpanded;

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

}
