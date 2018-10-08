package es.iessaladillo.pedrojoya.pr105.ui.main.option2.tab1;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;
import es.iessaladillo.pedrojoya.pr105.data.Repository;

class Option2Tab1ViewModelFactory implements Factory {

    private final Repository repository;

    public Option2Tab1ViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new Option2Tab1ViewModel(repository);
    }

}
