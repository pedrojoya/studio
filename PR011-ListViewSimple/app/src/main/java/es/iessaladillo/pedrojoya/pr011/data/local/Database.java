package es.iessaladillo.pedrojoya.pr011.data.local;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class Database {

    public static final String TABLE_STUDENTS = "STUDENTS";

    private static volatile Database instance;

    private final ArrayList<String> students;

    private Database() {
        students = new ArrayList<>();
    }

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

    public <T> List<T> selectAll(String tableName) {
        if (TextUtils.equals(tableName, TABLE_STUDENTS)) {
            //noinspection unchecked
            return (List<T>) students;
        } else {
            throw new RuntimeException("Table unkown in database");
        }
    }

    public <T> boolean insert(String tableName, T item) {
        if (TextUtils.equals(tableName, TABLE_STUDENTS)) {
            return students.add((String) item);
        } else {
            throw new RuntimeException("Table unkown in database");
        }
    }

    public <T> boolean delete(String tableName, T item) {
        if (TextUtils.equals(tableName, TABLE_STUDENTS)) {
            return students.remove((String) item);
        } else {
            throw new RuntimeException("Table unkown in database");
        }
    }

}
