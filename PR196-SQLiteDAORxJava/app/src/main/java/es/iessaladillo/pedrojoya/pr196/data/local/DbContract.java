package es.iessaladillo.pedrojoya.pr196.data.local;

import android.provider.BaseColumns;

public class DbContract {

    public static final String DB_NAME = "school";

    public static final int DB_VERSION = 1;

    private DbContract() {
    }

    // Students table.
    public abstract static class Student implements BaseColumns {
        public static final String TABLE_NAME = "students";
        public static final String NAME = "name";
        public static final String GRADE = "grade";
        public static final String PHONE = "phone";
        public static final String ADDRESS = "address";
        public static final String[] ALL_FIELDS = new String[]{_ID, NAME, GRADE, PHONE, ADDRESS};
        public static final String NOTIFICATION_URI = "content://es.iessaladillo.school/students";
        public static final String DROP_TABLE_QUERY = "drop table if exists " + TABLE_NAME;
    }

}
