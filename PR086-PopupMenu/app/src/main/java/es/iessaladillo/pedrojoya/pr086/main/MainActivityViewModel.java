package es.iessaladillo.pedrojoya.pr086.main;

import android.arch.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

import es.iessaladillo.pedrojoya.pr086.data.model.Student;

class MainActivityViewModel extends ViewModel {

    private List<Student> students;

    public List<Student> getStudents() {
        if (students == null) {
            students = Arrays.asList(
                    new Student("Baldomero Llégate Ligero", "La casa de Baldomero", "956956956",
                            "2º CFGS DAM"),
                    new Student("Germán Ginés de todos los Santos", "La casa de Germán",
                            "678678678", "1º CFGS DAM"),
                    new Student("Dolores Fuertes de Barriga", "La casa de Dolores", "666666666",
                            "1º CFGM SMR"),
                    new Student("Jorge Javier Jiménez Jaén", "La casa de Jorge", "688688688",
                            "1º CFGM SMR"),
                    new Student("Fabián Gonzáles Palomino", "La casa de Fabián", "999999999",
                            "2º CFGM SMR"));
        }
        return students;
    }

}
