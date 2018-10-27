package es.iessaladillo.pedrojoya.pr011.ui.main;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import es.iessaladillo.pedrojoya.pr011.R;
import es.iessaladillo.pedrojoya.pr011.data.local.Database;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.schibsted.spain.barista.assertion.BaristaEnabledAssertions.assertDisabled;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;
import static com.schibsted.spain.barista.interaction.BaristaEditTextInteractions.writeTo;
import static com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions.closeKeyboard;
import static es.iessaladillo.pedrojoya.pr011.Matchers.AdapterMatchers.withAdaptedData;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public final IntentsTestRule<MainActivity> testRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        closeKeyboard();
        Database.getInstance().deleteAll(Database.TABLE_STUDENTS);
    }

    // Initial state

    @Test
    public void shouldAddButtonBeDisabledInitially() {
        assertDisabled(R.id.btnAdd);
    }

    // List

    @Test
    public void shouldAddStudent() {
        String student = "Test";
        writeTo(R.id.txtName, student);
        clickOn(R.id.btnAdd);
        onData(allOf(is(instanceOf(String.class)), is(student)))
            .inAdapterView(withId(R.id.lstStudents))
            .check(matches(isDisplayed()));
    }

    @Test
    public void shouldDeleteStudent() {
        String student1 = "Test1";
        String student2 = "Test2";
        writeTo(R.id.txtName, student1);
        clickOn(R.id.btnAdd);
        writeTo(R.id.txtName, student2);
        clickOn(R.id.btnAdd);
        onData(allOf(is(instanceOf(String.class)), is(student1)))
            .inAdapterView(withId(R.id.lstStudents))
            .perform(longClick());
        onView(withId(R.id.lstStudents))
            .check(matches(not(withAdaptedData(is(student1)))));
    }

    @Test
    public void shouldShowToastWhenStudentClicked() {
        String student1 = "Test1";
        writeTo(R.id.txtName, student1);
        clickOn(R.id.btnAdd);
        onData(allOf(is(instanceOf(String.class)), is(student1)))
            .inAdapterView(withId(R.id.lstStudents))
            .perform(click());
        onView(withText(student1)).inRoot(withDecorView(not(testRule.getActivity().getWindow()
            .getDecorView()))).check(matches(isDisplayed()));
    }

    // Empty list

    @Test
    public void shouldEmptyViewBeVisibleWhenNoData() {
        String student1 = "Test1";
        writeTo(R.id.txtName, student1);
        clickOn(R.id.btnAdd);
        onData(allOf(is(instanceOf(String.class)), is(student1)))
            .inAdapterView(withId(R.id.lstStudents))
            .perform(longClick());
        assertDisplayed(R.id.lblEmptyView);
    }

    @Test
    public void shouldListViewBeInvisibleWhenNoData() {
        String student1 = "Test1";
        writeTo(R.id.txtName, student1);
        clickOn(R.id.btnAdd);
        onData(allOf(is(instanceOf(String.class)), is(student1)))
            .inAdapterView(withId(R.id.lstStudents))
            .perform(longClick());
        assertNotDisplayed(R.id.lstStudents);
    }

}
