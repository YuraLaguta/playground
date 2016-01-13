package au.com.happydev.atlassiantest.messages;


import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import au.com.happydev.atlassiantest.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by laguta.yurii@gmail.com on 13/01/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TransformationActivityTest {

    @Rule
    public ActivityTestRule<TransformationActivity> mActivityRule = new ActivityTestRule<>(TransformationActivity.class);

    @Test
    public void transformDefaultMessage() throws InterruptedException {
        onView(withText(R.string.transformation_transform_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.transformation_progress_bar)).check(matches(Matchers.not(isDisplayed())));
        onView(withText(R.string.transformation_transform_btn)).perform(ViewActions.click());
        onView(withId(R.id.transformation_progress_bar)).check(matches(Matchers.not(isDisplayed())));
        onView(withId(R.id.transformation_msg_text_view)).check(matches(withText(Matchers.containsString("androidweekly"))));
        onView(withId(R.id.transformation_send_btn)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isEnabled())));
        onView(withId(R.id.transformation_edit_text)).check(ViewAssertions.matches(withText("")));
    }

    @Test
    public void thatBtnIsDisabledWhenMessageIsEmpty() throws InterruptedException {
        onView(withId(R.id.transformation_send_btn)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        onView(withId(R.id.transformation_edit_text)).perform(ViewActions.clearText());
        onView(withId(R.id.transformation_send_btn)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isEnabled())));
        onView(withId(R.id.transformation_edit_text)).perform(ViewActions.typeText("@Matt another test has passed"));
        onView(withId(R.id.transformation_send_btn)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
    }
}