package es.iessaladillo.pedrojoya.pr102.ui.edit;

import java.util.Calendar;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr102.reminder.ReminderScheduler;

class EditActivityViewModel extends ViewModel {

    private final ReminderScheduler reminderScheduler;

    public EditActivityViewModel(ReminderScheduler reminderScheduler) {
        this.reminderScheduler = reminderScheduler;
    }

    void rescheduleReminder(String message, Calendar calendar) {
        reminderScheduler.createClockAlarm(message, calendar);
    }

    void cancelReminder() {
        reminderScheduler.cancelReminder();
    }

}
