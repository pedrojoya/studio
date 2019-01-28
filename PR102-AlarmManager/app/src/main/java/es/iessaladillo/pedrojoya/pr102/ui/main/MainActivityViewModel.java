package es.iessaladillo.pedrojoya.pr102.ui.main;

import java.util.Calendar;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr102.reminder.ReminderScheduler;

class MainActivityViewModel extends ViewModel {

    private final ReminderScheduler reminderScheduler;

    MainActivityViewModel(ReminderScheduler reminderScheduler) {
        this.reminderScheduler = reminderScheduler;
    }

    void createReminder(String message, Calendar calendar) {
        reminderScheduler.createReminder(message, calendar);
    }

    void createClockAlarm(String message, Calendar calendar) {
        reminderScheduler.createClockAlarm(message, calendar);
    }

    void sendToClockApp(String message, Calendar calendar) {
        reminderScheduler.sendToClockApp(message, calendar);
    }

}
