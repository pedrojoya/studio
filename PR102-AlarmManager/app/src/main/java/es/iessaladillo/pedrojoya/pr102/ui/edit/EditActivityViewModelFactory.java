package es.iessaladillo.pedrojoya.pr102.ui.edit;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.pr102.reminder.ReminderScheduler;

class EditActivityViewModelFactory implements ViewModelProvider.Factory {

    private final ReminderScheduler reminderScheduler;

    EditActivityViewModelFactory(ReminderScheduler reminderScheduler) {
        this.reminderScheduler = reminderScheduler;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EditActivityViewModel.class)) {
            return (T) new EditActivityViewModel(reminderScheduler);
        } else {
            throw new IllegalArgumentException("Wrong viewModel class");
        }
    }

}
