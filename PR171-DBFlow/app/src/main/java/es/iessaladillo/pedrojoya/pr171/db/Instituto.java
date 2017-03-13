package es.iessaladillo.pedrojoya.pr171.db;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.migration.BaseMigration;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

@Database(name = Instituto.BD_NOMBRE, version = Instituto.BD_VERSION)
public class Instituto {

    public static final String BD_NOMBRE = "instituto";
    public static final int BD_VERSION = 1;

    private Instituto() { }

    @Migration(version = 0, database = Instituto.class)
    public static class Migration0 extends BaseMigration {

        @Override
        public void migrate(DatabaseWrapper database) {
            // Datos iniciales de la tabla Curso.
            database.execSQL("INSERT INTO Curso VALUES (null, '1º CFGM SMR')");
            database.execSQL("INSERT INTO Curso VALUES (null, '2º CFGM SMR')");
            database.execSQL("INSERT INTO Curso VALUES (null, '1º CFGS DAM')");
            database.execSQL("INSERT INTO Curso VALUES (null, '2º CFGM DAM')");
            // Datos iniciales de la tabla Asignatura.
            database.execSQL("INSERT INTO Asignatura VALUES (null, 'PMDMO')");
            database.execSQL("INSERT INTO Asignatura VALUES (null, 'PSPRO')");
        }

    }

}
