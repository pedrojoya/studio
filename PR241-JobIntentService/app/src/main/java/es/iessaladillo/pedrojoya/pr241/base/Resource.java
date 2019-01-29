package es.iessaladillo.pedrojoya.pr241.base;

public class Resource<T> {

    enum Status { SUCCESS, ERROR, LOADING }

    private final Resource.Status status;
    private final T data;
    private final Exception exception;

    private Resource(Status status, T data, Exception exception) {
        this.status = status;
        this.data = data;
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

    public Exception getException() {
        return exception;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(Exception exception) {
        return new Resource<>(Status.ERROR, null, exception);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(Status.LOADING, null, null);
    }

}
