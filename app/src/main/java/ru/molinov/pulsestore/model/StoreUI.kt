package ru.molinov.pulsestore.model

import java.text.SimpleDateFormat
import java.util.*

data class StoreUI(
    override val time: String,
    val systolic: String,
    val dystolic: String,
    val pulse: String
) : Items() {

    internal fun toDB(): StoreDB {
        return StoreDB(
            getDateTime(time),
            systolic.toInt(),
            dystolic.toInt(),
            pulse.toInt()
        )
    }

    private fun getDateTime(date: String): Long {
        val sdf = SimpleDateFormat(STORE_TIME_PATTERN, Locale.getDefault())
        return sdf.parse(date)?.time ?: throw NullPointerException("$date is null")
    }

    companion object {
        const val STORE_TIME_PATTERN = "HH:mm"
    }
}
