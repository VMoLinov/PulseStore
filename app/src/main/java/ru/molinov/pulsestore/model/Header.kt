package ru.molinov.pulsestore.model

import java.text.SimpleDateFormat
import java.util.*

data class Header(
    override val time: String
) : Items() {

    companion object {
        const val HEADER_TIME_PATTERN = "dd MMMM"
    }
}

internal fun getHeaderTime(date: Long): String {
    val sdf = SimpleDateFormat(Header.HEADER_TIME_PATTERN, Locale.getDefault())
    return sdf.format(date)
}
