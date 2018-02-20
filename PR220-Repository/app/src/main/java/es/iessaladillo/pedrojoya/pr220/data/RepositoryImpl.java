package es.iessaladillo.pedrojoya.pr220.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import es.iessaladillo.pedrojoya.pr220.Constants;
import es.iessaladillo.pedrojoya.pr220.data.local.AppDatabase;
import es.iessaladillo.pedrojoya.pr220.data.model.Student;
import es.iessaladillo.pedrojoya.pr220.data.remote.StudentsApi;
import es.iessaladillo.pedrojoya.pr220.data.remote.requests.DeleteStudentCriteria;
import es.iessaladillo.pedrojoya.pr220.data.remote.responses.StudentResponse;
import es.iessaladillo.pedrojoya.pr220.utils.DatabaseUtils;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final AppDatabase db;
    private final StudentsApi api;

    private RepositoryImpl(Context context) {
        db = buildDatabase(context);
        api = buildStudentsApiClient(context);
    }

    @NonNull
    private AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, Constants.DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        DatabaseUtils.executeSqlFromAssetsFile(db, db.getVersion(), context.getAssets());
                    }
                })
                .build();
    }

    private StudentsApi buildStudentsApiClient(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(
                new ChuckInterceptor(context))
                .connectTimeout(Constants.API_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(Constants.API_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(Constants.API_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
        Log.d("Mia", String.valueOf(okHttpClient.connectTimeoutMillis()));
        Log.d("Mia", String.valueOf(okHttpClient.readTimeoutMillis()));
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(StudentsApi.class);
    }


    public static synchronized RepositoryImpl getInstance(Context context) {
        if (instance == null) {
            instance = new RepositoryImpl(context);
        }
        return instance;
    }

    @Override
    public LiveData<List<Student>> getStudents() {
        return db.studentDao().getStudents();
    }

    @Override
    public LiveData<Student> getStudent(String studentId) {
        return db.studentDao().getStudent(studentId);
    }

    @Override
    public Single<StudentResponse> addStudent(Student student) {
        return api.insertStudent(student)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(list -> db.studentDao().insertStudent(student));
    }

    @Override
    public Single<List<StudentResponse>> updateStudent(Student student) {
        return api.updateStudent(student.getId(), student)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(list -> db.studentDao().updateStudent(student));
    }

    @Override
    public Single<List<StudentResponse>> deleteStudent(Student student) {
        return api.deleteStudent(new DeleteStudentCriteria(student))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(list -> db.studentDao().deleteStudent(student));
    }

    @Override
    public Single<List<Student>> loadStudentsFromApi() {
        return api.getStudents()
                .flatMapIterable(list -> list)
                .map(Student::new)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess(list -> db.studentDao().resetStudents(list));
    }

}
