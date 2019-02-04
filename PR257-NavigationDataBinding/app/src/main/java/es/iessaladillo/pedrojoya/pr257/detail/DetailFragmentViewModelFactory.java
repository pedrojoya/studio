package es.iessaladillo.pedrojoya.pr257.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class DetailFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Bundle arguments;

    DetailFragmentViewModelFactory(Bundle arguments) {
        this.arguments = arguments;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailFragmentViewModel.class)) {
            return (T) new DetailFragmentViewModel(arguments);
        } else {
            throw new IllegalArgumentException("Wrong viewModel class");
        }
    }

}
