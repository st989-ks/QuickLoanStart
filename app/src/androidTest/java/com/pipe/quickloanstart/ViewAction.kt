
import android.view.View
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Matcher
import java.lang.Thread.sleep
import java.util.concurrent.TimeoutException


/**
 * Perform action of waiting for a specific view id.
 * @param viewId The id of the view to wait for.
 * @param millis The timeout of until when to wait for.
 */
fun waitId(viewId: Int, millis: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun getDescription(): String {
            return "wait for a specific view with id <$viewId> during $millis millis."
        }

        override fun perform(uiController: UiController, view: View?) {
            uiController.loopMainThreadUntilIdle()
            val startTime = System.currentTimeMillis()
            val endTime = startTime + millis
            val viewMatcher: Matcher<View> = withId(viewId)
            do {
                for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                    // found view with required ID
                    if (viewMatcher.matches(child)) {
                        return
                    }
                }
                uiController.loopMainThreadForAtLeast(50)
            } while (System.currentTimeMillis() < endTime)
            throw PerformException.Builder()
                .withActionDescription(this.description)
                .withViewDescription(HumanReadables.describe(view))
                .withCause(TimeoutException())
                .build()
        }
    }
}

fun waitText(viewString: String, millis: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun getDescription(): String {
            return "wait for a specific view with text <$viewString> during $millis millis."
        }

        override fun perform(uiController: UiController, view: View?) {
            uiController.loopMainThreadUntilIdle()
            val startTime = System.currentTimeMillis()
            val endTime = startTime + millis
            val viewMatcher: Matcher<View> = withText(viewString)
            do {
                for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                    // found view with required ID
                    if (viewMatcher.matches(child)) {
                        return
                    }
                }
                uiController.loopMainThreadForAtLeast(50)
            } while (System.currentTimeMillis() < endTime)
            throw PerformException.Builder()
                .withActionDescription(this.description)
                .withViewDescription(HumanReadables.describe(view))
                .withCause(TimeoutException())
                .build()
        }
    }
}

fun searchFor(matcher: Matcher<View>): ViewAction {

    return object : ViewAction {

        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun getDescription(): String {
            return "searching for view $matcher in the root view"
        }

        override fun perform(uiController: UiController, view: View) {

            var tries = 0
            val childViews: Iterable<View> = TreeIterables.breadthFirstViewTraversal(view)

            // Look for the match in the tree of childviews
            childViews.forEach {
                tries++
                if (matcher.matches(it)) {
                    // found the view
                    return
                }
            }

            throw NoMatchingViewException.Builder()
                .withRootView(view)
                .withViewMatcher(matcher)
                .build()
        }
    }
}

fun waitForView(
    viewMatcher: Matcher<View>,
    waitMillis: Int = 5000,
    waitMillisPerTry: Long = 100
): ViewInteraction {

    // Derive the max tries
    val maxTries = waitMillis / waitMillisPerTry.toInt()

    var tries = 0

    for (i in 0..maxTries)
        try {
            // Track the amount of times we've tried
            tries++

            // Search the root for the view
            onView(isRoot()).perform(searchFor(viewMatcher))

            // If we're here, we found our view. Now return it
            return onView(viewMatcher)

        } catch (e: Exception) {

            if (tries == maxTries) {
                throw e
            }
            sleep(waitMillisPerTry)
        }

    throw Exception("Error finding a view matching $viewMatcher")
}

fun doOnView(matcher: Matcher<View>, vararg actions: ViewAction) {
    actions.forEach {
        waitForView(matcher).perform(it)
    }
}

fun assertOnView(matcher: Matcher<View>, vararg assertions: ViewAssertion) {
    assertions.forEach {
        waitForView(matcher).check(it)
    }
}