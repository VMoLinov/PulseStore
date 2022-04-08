package ru.molinov.pulsestore.remote

import ru.molinov.pulsestore.model.StoreDB
import ru.molinov.pulsestore.model.StoreUI

interface Network {

    fun saveData(data: StoreDB, callback: (List<StoreUI>) -> Unit)
    fun readData(callback: (List<StoreUI>) -> Unit)
}
