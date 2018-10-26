package es.iessaladillo.pedrojoya.pr011.ui.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import es.iessaladillo.pedrojoya.pr011.data.Repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityViewModelTest {

    @Mock
    private Repository repository;
    private MainActivityViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new MainActivityViewModel(repository);
    }

    @Test
    public void testGetStudents() {
        List<String> testStudents = Arrays.asList("Baldo", "Mero");
        when(repository.queryStudents()).thenReturn(testStudents);

        List<String> students = viewModel.getStudents();

        Mockito.verify(repository).queryStudents();
        assertThat(students, contains("Baldo", "Mero"));
    }

    @Test
    public void testAddStudent() {
        String studentToAdd = "German";

        viewModel.addStudent(studentToAdd);

        Mockito.verify(repository).addStudent(studentToAdd);
    }

    @Test
    public void testDeleteStudent() {
        String studentToDelete = "German";

        viewModel.deleteStudent(studentToDelete);

        Mockito.verify(repository).deleteStudent(studentToDelete);
    }

}