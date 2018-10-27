package es.iessaladillo.pedrojoya.pr011.data.local;

import android.text.TextUtils;

import java.util.ArrayList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;


public class Database {

    public static final String TABLE_STUDENTS = "STUDENTS";

    private static volatile Database instance;

    private final ArrayList<String> students;

    private Database() {
        students = new ArrayList<>();
    }

    /**
     * Returns the singleton instance of the Database
     *
     * @return The singleton instance of the Database
     */
    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    @NonNull
    public <T> List<T> selectAll(String tableName) {
        if (TextUtils.equals(tableName, TABLE_STUDENTS)) {
            //noinspection unchecked
            return (List<T>) new ArrayList<>(students);
        } else {
            throw new RuntimeException("Table unkown in database");
        }
    }

    public <T> int insert(String tableName, T item) {
        if (TextUtils.equals(tableName, TABLE_STUDENTS)) {
            return students.add((String) item) ? 1 : 0;
        } else {
            throw new RuntimeException("Table unkown in database");
        }
    }

    public <T> int delete(String tableName, T item) {
        if (TextUtils.equals(tableName, TABLE_STUDENTS)) {
            //noinspection SuspiciousMethodCalls
            return students.remove(item) ? 1 : 0;
        } else {
            throw new RuntimeException("Table unkown in database");
        }
    }

    @VisibleForTesting
    public <T> void deleteAll(String tableName) {
        if (TextUtils.equals(tableName, TABLE_STUDENTS)) {
            //noinspection SuspiciousMethodCalls
            students.clear();
        } else {
            throw new RuntimeException("Table unkown in database");
        }
    }

}
