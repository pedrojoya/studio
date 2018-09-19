package es.iessaladillo.pedrojoya.pr016.utils;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionUtils {

    public interface Predicate<T> {
        boolean test(T item);
    }

    public interface Function<T, R> {
        R apply(T t);
    }

    public static <T> Collection<T> filter(Collection<T> col, Predicate<T> predicate) {
        Collection<T> result = new ArrayList<>();
        for (T element: col) {
            if (predicate.test(element)) {
                result.add(element);
            }
        }
        return result;
    }

    public static <T, R> Collection<R> map(Collection<T> col, Function<T, R> function) {
        Collection<R> result = new ArrayList<>();
        for (T element: col) {
            result.add(function.apply(element));
        }
        return result;
    }

}
