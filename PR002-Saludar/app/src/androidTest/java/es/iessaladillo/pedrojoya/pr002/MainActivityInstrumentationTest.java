package es.iessaladillo.pedrojoya.pr002;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.iessaladillo.pedrojoya.pr002.ui.main.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

//
// VER DOCUMENTACIÓN EN https://google.github.io/android-testing-support-library/docs/index.html
// VER CODELAB EN https://codelabs.developers.google.com/codelabs/android-testing/index.html
//

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentationTest {

    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public final ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    // Valida que cambia el texto del checkbox al pulsarlo.
    @Test
    public void validateChangeCheckBoxTextOnClick() {
        onView(withId(R.id.chkPolite)).perform(click()).check(matches(withText(R.string.main_activity_impolite_mode)));
        onView(withId(R.id.chkPolite)).perform(click()).check(matches(withText(R.string.main_activity_polite_mode)));
    }

    // Valida que saluda educadamente.
    @Test
    public void validateSaludoEducado() {
        onView(withId(R.id.txtName)).perform(replaceText("Baldomero"));
        onView(withId(R.id.btnGreet)).perform(click());
        onView(withText("Buenos días tenga usted Baldomero"))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow()
                        .getDecorView()))).check(matches(isDisplayed()));
        // Se espera que desaparezca el Toast (para enlazar tests).
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateSaludoNoEducado() {
        onView(withId(R.id.txtName)).perform(replaceText("Baldomero"));
        onView(withId(R.id.chkPolite)).perform(click());
        onView(withId(R.id.btnGreet)).perform(click());
        onView(withText("Buenos días Baldomero")).inRoot(withDecorView(
                not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        // Se espera que desaparezca el Toast (para enlazar tests).
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}