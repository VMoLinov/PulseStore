package ru.molinov.pulsestore.remote

import com.google.firebase.firestore.FirebaseFirestore
import ru.molinov.pulsestore.model.StoreDB

class NetworkImpl(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) : Network {

    override fun saveData(data: StoreDB, callback: (List<StoreDB>) -> Unit) {
        val store = mutableMapOf<String, Number>(
            TIME to data.time,
            SYSTOLIC to data.systolic,
            DYSTOLIC to data.dystolic,
            PULSE to data.pulse
        )
        db.collection(STORE).add(store)
        readData(callback)
    }

    override fun readData(callback: (List<StoreDB>) -> Unit) {
        val list = mutableListOf<StoreDB>()
        db.collection(STORE)
            .orderBy(TIME)
            .get()
            .addOnSuccessListener { result ->
                for (item in result) {
                    list.add(
                        StoreDB(
                            item[TIME] as Long,
                            (item[SYSTOLIC] as Number).toInt(),
                            (item[DYSTOLIC] as Number).toInt(),
                            (item[PULSE] as Number).toInt()
                        )
                    )
                }
                callback.invoke(list)
            }
    }

    companion object {
        const val TIME = "time"
        const val SYSTOLIC = "systolic"
        const val DYSTOLIC = "dystolic"
        const val PULSE = "pulse"
        const val STORE = "Store"
    }
}
