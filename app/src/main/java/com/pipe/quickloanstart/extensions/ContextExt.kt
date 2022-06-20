package com.pipe.quickloanstart.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.pipe.quickloanstart.R
import java.util.*

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showGenericAlertDialog(message: String) {
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton("ok") { d, _ -> d.cancel() }
    }.show()
}


var dialog: ProgressDialog? = null
fun Context.showLoader(isShow: Boolean) {
    if (isShow) {
        dialog = ProgressDialog(this)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        dialog?.show()
        dialog?.setContentView(R.layout.progress_bar)
    } else {
        dialog?.dismiss()
        dialog = null
    }

}

fun Context.openUrl(link: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    this.startActivity(intent)
}

fun Context.setLocale(languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val resources: Resources = resources
    val config: Configuration = resources.configuration
    config.setLocale(locale)
    resources.updateConfiguration(config, resources.displayMetrics)
}

fun Context.getLocaleStringResource(
    requestedLocale: Locale?,
    resourceId: Int,
): String {
    val result: String
    val config =
        Configuration(resources.configuration)
    config.setLocale(requestedLocale)
    result = createConfigurationContext(config).getText(resourceId).toString()
    return result
}

@SuppressLint("UseCompatLoadingForDrawables")
fun Context.getLocaleDrawableResource(
    requestedLocale: Locale?,
    resourceId: Int,
): Drawable {
    val result: Drawable
    val config =
        Configuration(resources.configuration)
    config.setLocale(requestedLocale)
    result = createConfigurationContext(config).getDrawable(resourceId)!!
    return result
}

fun Fragment.hideKeyboard() {
    view?.let { requireActivity().hideKeyboard(it) }
}

fun Fragment.showKeyboard(view: View) {
    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
