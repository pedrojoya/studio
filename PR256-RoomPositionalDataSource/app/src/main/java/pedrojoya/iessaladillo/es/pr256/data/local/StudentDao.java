package pedrojoya.iessaladillo.es.pr256.data.local;

import com.mooveit.library.Fakeit;

import java.util.Random;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import pedrojoya.iessaladillo.es.pr256.base.BaseDao;
import pedrojoya.iessaladillo.es.pr256.data.local.model.Student;

@Dao
public abstract class StudentDao extends BaseDao<Student> {

    private static final String BASE_URL = "https://picsum.photos/200/300?image=";

    private static final Random random = new Random();

    @Query("SELECT * FROM Student ORDER BY name")
    public abstract DataSource.Factory<Integer, Student> queryAllStudents();


    @SuppressWarnings("SameParameterValue")
    @Transaction
    void insertInitialStudents(int size) {
        for (int i = 0; i < size; i++) {
            insert(createFakeStudent());
        }

    }

    public static Student createFakeStudent() {
        return new Student(0, Fakeit.name().name(), Fakeit.address().streetAddress(),
            BASE_URL + random.nextInt(1084));
    }

}
