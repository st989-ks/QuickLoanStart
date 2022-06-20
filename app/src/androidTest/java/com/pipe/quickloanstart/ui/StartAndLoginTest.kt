
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StartAndLoginTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun checkId() {
        onView(withId(R.id.btn_personal_loan)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_loans_list)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_loans_list)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.btn_close_status)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.btn_personal_loan)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.edit_amount)).perform(typeText("10"))
        onView(withId(R.id.edit_firstName)).perform(typeText("SSDDD"))
        onView(withId(R.id.edit_lastName)).perform(typeText("SSDDD"))
        onView(withId(R.id.edit_phoneNumber)).perform(typeText("46534353"))
        Thread.sleep(1000)
        onView(withId(R.id.btn_request_loan)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.btn_close_status)).perform(click())
        onView(withId(R.id.fab_big)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.fab_relogin)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.card_view_login)).check(matches(isDisplayed()))
        onView(withId(R.id.edit_text_username)).perform(typeText("user191111811111"))
        onView(withId(R.id.edit_text_password)).perform(typeText("user191111811111"))
        onView(withId(R.id.btn_sign_in)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.recycler_manual_list)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_close)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.recycler_loans_list)).check(matches(isDisplayed()))
    }
}