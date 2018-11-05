package es.iessaladillo.pedrojoya.PR004;

import android.app.SearchManager;
import android.content.Intent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.matcher.UriMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;
import es.iessaladillo.pedrojoya.PR004.ui.main.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTests {

    @SuppressWarnings("unchecked")
    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule(MainActivity.class);

    @Test
    public void shouldNavigate() {
        onView(withId(R.id.btnShowInBrowser)).perform(click());
        intended(allOf(
                //toPackage("com.android.browser"),
                hasAction(Intent.ACTION_VIEW),
                hasData(UriMatchers.hasScheme("http"))
                )
        );
    }

    @Test
    public void shouldWebSearch() {
        onView(withId(R.id.btnSearch)).perform(click());
        intended(allOf(
                //toPackage("com.android.quicksearchbox"),
                hasAction(Intent.ACTION_WEB_SEARCH),
                hasExtraWithKey(SearchManager.QUERY)
                )
        );
    }

    @Test
    public void shouldDial() {
        onView(withId(R.id.btnDial)).perform(click());
        intended(allOf(
                //toPackage("com.android.dialer"),
                hasData(UriMatchers.hasScheme("tel")),
                hasAction(Intent.ACTION_DIAL)
                )
        );
    }

    @Test
    public void shouldShowInMap() {
        onView(withId(R.id.btnShowInMap)).perform(click());
        intended(allOf(
                //toPackage("com.google.android.apps.maps")
                hasAction(Intent.ACTION_VIEW),
                hasData(UriMatchers.hasScheme("geo"))
                )
        );
    }

    @Test
    public void shouldSearchInMap() {
        onView(withId(R.id.btnSearchInMap)).perform(click());
        intended(allOf(
                //toPackage("com.google.android.apps.maps")
                hasAction(Intent.ACTION_VIEW),
                hasData(UriMatchers.hasScheme("geo"))
                )
        );
    }

    @Test
    public void shouldShowContacts() {
        onView(withId(R.id.btnShowContacts)).perform(click());
        intended(allOf(
                //toPackage("com.android.contacts")
                hasAction(Intent.ACTION_VIEW),
                hasData(UriMatchers.hasScheme("content"))
                )
        );
    }


}
