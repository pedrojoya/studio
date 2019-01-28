package es.iessaladillo.pedrojoya.pr102.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.pr102.reminder.ReminderScheduler;

class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final ReminderScheduler reminderScheduler;

    MainActivityViewModelFactory(ReminderScheduler reminderScheduler) {
        this.reminderScheduler = reminderScheduler;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(reminderScheduler);
        } else {
            throw new IllegalArgumentException("Wrong viewModel class");
        }
    }

}
