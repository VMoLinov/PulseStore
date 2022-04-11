package ru.molinov.pulsestore.remote

import ru.molinov.pulsestore.model.StoreDB

interface Network {

    fun saveData(data: StoreDB, callback: (List<StoreDB>) -> Unit)
    fun readData(callback: (List<StoreDB>) -> Unit)
}
