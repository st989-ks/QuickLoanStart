package com.pipe.quickloanstart.extensions

import com.pipe.quickloanstart.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.formatDate(): String {
    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    val result: LocalDateTime = LocalDateTime.parse(this, format)
    return result.format(DateTimeFormatter.ofPattern("dd.MM.yyyy "))
}

fun String.getStatus(): Int {
    return when (this) {
        "REJECTED" -> R.string.rejected
        "APPROVED" -> R.string.approved
        "REGISTERED" -> R.string.registered
        else -> R.string.unknown_state
    }
}



