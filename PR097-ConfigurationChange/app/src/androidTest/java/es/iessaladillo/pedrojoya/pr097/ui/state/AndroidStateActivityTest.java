package es.iessaladillo.pedrojoya.pr097.ui.state;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import es.iessaladillo.pedrojoya.pr097.R;
import es.iessaladillo.pedrojoya.pr097.utils.Rotation;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;
import static com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AndroidStateActivityTest {

    @Rule
    public final IntentsTestRule<AndroidStateActivity> testRule = new IntentsTestRule<>(AndroidStateActivity.class);

    @Test
    public void shouldStartMethodStartThisActivity() {
        AndroidStateActivity.start(testRule.getActivity());
        intended(hasComponent(AndroidStateActivity.class.getName()));
    }

    // Initial state.

    @Test
    public void shouldShowScore0Initially() {
        assertDisplayed(R.id.lblScore, "0");
    }

    // Increment

    @Test
    public void shouldIncrementScoreWhenIncrementButtonClicked() {
        clickOn(R.id.btnIncrement);
        assertDisplayed(R.id.lblScore, "1");
    }

    // Rotation

    @Test
    public void shouldPreserveScoreAfterRotation() {
        clickOn(R.id.btnIncrement);
        assertDisplayed(R.id.lblScore, "1");
        Rotation.rotateScreen(testRule.getActivity());
        assertDisplayed(R.id.lblScore, "1");
    }

}
