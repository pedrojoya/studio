package es.iessaladillo.pedrojoya.pr092.base;

@SuppressWarnings("unused")
public class Event<T> {

    private final T content;
    private boolean hasBeenHandled = false;

    public Event(T content) {
        this.content = content;
    }

    public boolean hasBeenHandled() {
        return hasBeenHandled;
    }

    public T getContentIfNotHandled() {
        if (hasBeenHandled) return null;
        hasBeenHandled = true;
        return content;
    }

    public T peekContent() {
        return content;
    }

}
