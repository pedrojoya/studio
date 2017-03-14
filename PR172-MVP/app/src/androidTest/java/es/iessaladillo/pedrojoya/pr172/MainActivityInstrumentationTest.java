package es.iessaladillo.pedrojoya.pr172;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.iessaladillo.pedrojoya.pr172.saludo.SaludoActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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
    public final ActivityTestRule<SaludoActivity> activityTestRule =
            new ActivityTestRule<>(SaludoActivity.class);

    // Valida que cambia el texto del checkbox al pulsarlo.
    @Test
    public void validateChangeCheckBoxTextOnClick() {
        onView(withId(R.id.chkEducado)).perform(click()).check(matches(withText(R.string.saludar_educadamente)));
        onView(withId(R.id.chkEducado)).perform(click()).check(matches(withText(R.string.saludar_normal)));
    }

    // Valida que saluda educadamente.
    @Test
    public void validateSaludoEducado() {
        onView(withId(R.id.txtNombre)).perform(typeText("Baldomero"));
        onView(withId(R.id.chkEducado)).perform(click());
        onView(withId(R.id.btnSaludar)).perform(click());
        onView(withText("Buenos días tenga usted Baldomero")).inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
        // Para que tiempo a ocultarse la Toast (para futuros test)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.chkEducado)).perform(click());
    }

    @Test
    public void validateSaludoNoEducado() {
        onView(withId(R.id.txtNombre)).perform(typeText("Baldomero"));
        onView(withId(R.id.btnSaludar)).perform(click());
        onView(withText("Buenos días Baldomero")).inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
        // Para que tiempo a ocultarse la Toast (para futuros test)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}