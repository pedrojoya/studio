package es.iessaladillo.pedrojoya.pr092.base;

public abstract class RequestState<T> {

    public static final class Error<T> extends RequestState<T> {

        private final Event<Exception> exception;

        public Error(Event<Exception> exception) {
            this.exception = exception;
        }

        public Event<Exception> getException() {
            return exception;
        }

    }

    public static final class Loading<T> extends RequestState<T> { }

    public static final class Result<M> extends RequestState<M> {

        private final M data;

        public Result(M data) {
            this.data = data;
        }

        @SuppressWarnings("unused")
        public M getData() {
            return data;
        }

    }

}

