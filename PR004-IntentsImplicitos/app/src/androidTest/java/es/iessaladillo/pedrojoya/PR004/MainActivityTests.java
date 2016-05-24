package es.iessaladillo.pedrojoya.PR004;

import android.app.SearchManager;
import android.content.Intent;
import android.support.test.espresso.intent.matcher.UriMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTests {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule(MainActivity.class);

    @Test
    public void validateNavegar() {
        onView(withId(R.id.btnNavegar)).perform(click());
        intended(allOf(
                //toPackage("com.android.browser"),
                hasAction(Intent.ACTION_VIEW),
                hasData(UriMatchers.hasScheme("http"))
                )
        );
    }

    @Test
    public void validateBuscar() {
        onView(withId(R.id.btnBuscar)).perform(click());
        intended(allOf(
                //toPackage("com.android.quicksearchbox"),
                hasAction(Intent.ACTION_WEB_SEARCH),
                hasExtraWithKey(SearchManager.QUERY)
                )
        );
    }

    @Test
    public void validateLlamar() {
        onView(withId(R.id.btnLlamar)).perform(click());
        intended(allOf(
                //toPackage("com.android.server.telecom"),
                hasAction(Intent.ACTION_CALL),
                hasData(UriMatchers.hasScheme("tel"))
                )
        );
    }

    @Test
    public void validateMarcar() {
        onView(withId(R.id.btnMarcar)).perform(click());
        intended(allOf(
                //toPackage("com.android.dialer"),
                hasData(UriMatchers.hasScheme("tel")),
                hasAction(Intent.ACTION_DIAL)
                )
        );
    }

    @Test
    public void validateMostrarMapa() {
        onView(withId(R.id.btnMostrarMapa)).perform(click());
        intended(allOf(
                //toPackage("com.google.android.apps.maps")
                hasAction(Intent.ACTION_VIEW),
                hasData(UriMatchers.hasScheme("geo"))
                )
        );
    }

    @Test
    public void validateBuscarMapa() {
        onView(withId(R.id.btnBuscarMapa)).perform(click());
        intended(allOf(
                //toPackage("com.google.android.apps.maps")
                hasAction(Intent.ACTION_VIEW),
                hasData(UriMatchers.hasScheme("geo"))
                )
        );
    }

    @Test
    public void validateMostrarContactos() {
        onView(withId(R.id.btnMostrarContactos)).perform(click());
        intended(allOf(
                //toPackage("com.android.contacts")
                hasAction(Intent.ACTION_VIEW),
                hasData(UriMatchers.hasScheme("content"))
                )
        );
    }


}
