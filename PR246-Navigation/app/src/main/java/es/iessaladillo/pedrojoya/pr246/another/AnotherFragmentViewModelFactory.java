package es.iessaladillo.pedrojoya.pr246.another;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class AnotherFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Bundle arguments;

    AnotherFragmentViewModelFactory(Bundle arguments) {
        this.arguments = arguments;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AnotherFragmentViewModel.class)) {
            return (T) new AnotherFragmentViewModel(arguments);
        } else {
            throw new IllegalArgumentException("Wrong viewModel class");
        }
    }

}
