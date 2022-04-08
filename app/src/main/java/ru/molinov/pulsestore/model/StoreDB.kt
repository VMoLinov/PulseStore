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
        return try {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val netDate = Date(timeStamp)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}
