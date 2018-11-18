package es.iessaladillo.pedrojoya.pr059.data;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface Repository {

    LiveData<List<String>> queryStudents(String criteria);

}
