package es.iessaladillo.pedrojoya.pr082.base;

public abstract class RequestState {

    public static final class Error extends RequestState {

        private final Event<Exception> exception;

        public Error(Event<Exception> exception) {
            this.exception = exception;
        }

        public Event<Exception> getException() {
            return exception;
        }

    }

    public static final class Loading extends RequestState {

        private final boolean loading;

        public Loading(boolean loading) {
            this.loading = loading;
        }

        public boolean isLoading() {
            return loading;
        }

    }

    public static final class Result<T> extends RequestState {

        private final T data;

        public Result(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

    }

}

