package es.iessaladillo.pedrojoya.pr244.base;

public class Event<T> {

    private final T content;
    private boolean handled;

    public Event(T content) {
        this.content = content;
    }

    public T getContentIfNotHandled() {
        if (handled) return null;
        handled = true;
        return content;
    }

    @SuppressWarnings("unused")
    public T peekContent() {
        return content;
    }

}
