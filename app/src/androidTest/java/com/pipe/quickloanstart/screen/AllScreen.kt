
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen

object AllScreen: Screen<AllScreen>()  {
    val editTextUsername = KEditText { withId(R.id.edit_text_username) }
    val editTextPassword = KEditText { withId(R.id.edit_text_password) }
    val editAmount = KEditText { withId(R.id.edit_amount) }
    val editFirstName = KEditText { withId(R.id.edit_firstName) }
    val editLastName = KEditText { withId(R.id.edit_lastName) }
    val editPhoneNumber = KEditText { withId(R.id.edit_phoneNumber) }
}