package es.iessaladillo.pedrojoya.pr180.base;

import androidx.lifecycle.Observer;

public class EventObserver<T> implements Observer<Event<T>> {

    private final OnEventUnhandledContent<T> onEventUnhandledContent;

    public EventObserver(OnEventUnhandledContent<T> onEventUnhandledContent) {
        this.onEventUnhandledContent = onEventUnhandledContent;
    }

    @Override
    public void onChanged(Event<T> event) {
        if (event != null) {
            T value = event.getContentIfNotHandled();
            if (value != null) {
                onEventUnhandledContent.handle(value);
            }
        }
    }

    public interface OnEventUnhandledContent<T> {
        void handle(T value);
    }

}
