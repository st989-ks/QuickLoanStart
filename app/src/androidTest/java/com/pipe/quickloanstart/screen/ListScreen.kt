
import android.view.View
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object ListScreen : Screen<ListScreen>() {

    val loansList = KRecyclerView(
        builder = { withId(R.id.recycler_loans_list) },
        itemTypeBuilder = { itemType(::LoansListItem) }
    )

    class LoansListItem(parent: Matcher<View>) :
        KRecyclerItem<LoansListItem>(parent) {
        val title = KTextView(parent) { withId(R.id.loans_status_all) }
        val text = KTextView(parent) { withId(R.id.list_of_loans_status_all) }
    }
}