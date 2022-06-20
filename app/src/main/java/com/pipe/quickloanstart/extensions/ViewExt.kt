package com.pipe.quickloanstart.extensions
import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.pipe.quickloanstart.R


fun TextInputLayout.activeBorder(context: Context, active: Boolean) {
    this.boxStrokeColor =
        ContextCompat.getColor(context, if (active) R.color.blue else R.color.colorError)
}


fun TextView.textColorStatus(status: String) {
    when (status) {
        "REJECTED" -> setTextColor(context.getColor(R.color.orange))
        "APPROVED" -> setTextColor(context.getColor(R.color.green))
        "REGISTERED" -> setTextColor(context.getColor(R.color.colorError))
        else -> setTextColor(context.getColor(R.color.colorSurface))
    }
}

