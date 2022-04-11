package ru.molinov.pulsestore.model

import java.text.SimpleDateFormat
import java.util.*

data class StoreDB(
    val time: Long,
    val systolic: Int,
    val dystolic: Int,
    val pulse: Int
) {

    internal fun toUI(): StoreUI {
        return StoreUI(
            getDateTime(time),
            systolic.toString(),
            dystolic.toString(),
            pulse.toString()
        )
    }

    private fun getDateTime(timeStamp: Long): String {
        val sdf = SimpleDateFormat(StoreUI.STORE_TIME_PATTERN, Locale.getDefault())
        val netDate = Date(timeStamp)
        return sdf.format(netDate)
    }
}
