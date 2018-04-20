package es.iessaladillo.pedrojoya.pr164.ui.main;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.iessaladillo.pedrojoya.pr164.data.model.Group;
import es.iessaladillo.pedrojoya.pr164.data.model.Student;

public class MainActivityViewModel extends ViewModel {

    private List<Group> groups;

    public MainActivityViewModel() {
        groups = createGroups();
    }

    private List<Group> createGroups() {
        return new ArrayList<>(Arrays.asList(new Group(1, "CFGM Sistemas Microinformáticos y "
                        + "Redes",
                        new ArrayList<>(
                                Arrays.asList(new Student(1, "Baldomero", 18), new Student
                                                (3, "Sergio", 17),
                                        new Student(4, "Atanasio", 21), new Student
                                                (5, "Oswaldo", 26),
                                        new Student(6, "Rodrigo", 22), new Student(7, "Antonio",
                                                16)))),
                new Group(2, "CFGS Desarrollo de Aplicaciones Multiplataforma", new ArrayList<>(
                        Arrays.asList(new Student(8, "Pedro", 22), new Student(9, "Pablo", 22),
                                new Student(10, "Rodolfo", 21), new Student(11, "Gervasio", 24),
                                new Student(12, "Prudencia", 20), new Student(13, "Gumersindo",
                                        17),
                                new Student(14, "Gerardo", 18), new Student(15, "Óscar", 21)))
                )));
    }

    public List<Group> getGroups() {
        return groups;
    }

}
