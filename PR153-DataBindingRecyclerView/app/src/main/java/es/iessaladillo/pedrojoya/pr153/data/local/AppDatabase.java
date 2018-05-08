package es.iessaladillo.pedrojoya.pr153.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mooveit.library.Fakeit;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr153.data.local.model.Student;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private final Random random = new Random();

    public abstract StudentDao studentDao();

    public Student newFakeStudent() {
        return new Student(Fakeit.name().name(), Fakeit.address().streetAddress(),
                "http://lorempixel.com/100/100/abstract/" + (random.nextInt(10) + 1) + "/");
    }
    
}