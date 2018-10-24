package es.iessaladillo.pedrojoya.pr016.utils;

import java.util.ArrayList;
import java.util.Collection;

import androidx.annotation.NonNull;

public class CollectionUtils {

    public interface Predicate<T> {
        boolean test(T item);
    }

    public interface Function<T, R> {
        R apply(T t);
    }

    @NonNull
    public static <T> Collection<T> filter(@NonNull Collection<T> col, @NonNull Predicate<T> predicate) {
        Collection<T> result = new ArrayList<>();
        for (T element: col) {
            if (predicate.test(element)) {
                result.add(element);
            }
        }
        return result;
    }

    @NonNull
    public static <T, R> Collection<R> map(@NonNull Collection<T> col, @NonNull Function<T, R> function) {
        Collection<R> result = new ArrayList<>();
        for (T element: col) {
            result.add(function.apply(element));
        }
        return result;
    }

}
