package es.iessaladillo.pedrojoya.pr237.base;

@SuppressWarnings("unused")
public class Resource<T> {

    enum Status { SUCCESS, ERROR, LOADING }

    private final Resource.Status status;
    private final T data;
    private final Event<Exception> exception;
    private final Integer progress;

    private Resource(Status status, T data, Integer progress, Event<Exception> exception) {
        this.status = status;
        this.data = data;
        this.progress = progress;
        this.exception = exception;
    }

    @SuppressWarnings("unused")
    public Status getStatus() {
        return status;
    }

    public boolean isLoading() {
        return status == Status.LOADING;
    }

    public boolean hasError() {
        return status == Status.ERROR;
    }

    public boolean hasSuccess() {
        return status == Status.SUCCESS;
    }

    public T getData() {
        return data;
    }

    public Integer getProgress() {
        return progress;
    }

    public Event<Exception> getException() {
        return exception;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(Status.SUCCESS, data, null,null);
    }

    public static <T> Resource<T> error(Exception exception) {
        return new Resource<>(Status.ERROR, null, null, new Event<>(exception));
    }

    public static <T> Resource<T> loading(Integer progress) {
        return new Resource<>(Status.LOADING, null, progress, null);
    }

}
