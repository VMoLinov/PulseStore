package ru.molinov.pulsestore.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.molinov.pulsestore.model.Header
import ru.molinov.pulsestore.model.Items
import ru.molinov.pulsestore.model.StoreDB
import ru.molinov.pulsestore.model.getHeaderTime
import ru.molinov.pulsestore.remote.Network
import ru.molinov.pulsestore.remote.NetworkImpl

class MainViewModel(private val network: Network = NetworkImpl()) : ViewModel() {

    var liveData: MutableLiveData<List<Items>> = MutableLiveData()

    fun saveData(store: StoreDB) {
        Thread { network.saveData(store, ::dataChanged) }.start()
    }

    fun readData() {
        Thread { network.readData(::dataChanged) }.start()
    }

    private fun dataChanged(data: List<StoreDB>) {
        liveData.postValue(sortData(data))
    }

    private fun sortData(data: List<StoreDB>): List<Items> {
        val list = mutableListOf<Items>()
        for (item in data) {
            val time = getHeaderTime(item.time)
            if (!list.contains(Header(time))) { // Check header contains
                list.add(Header(time))
            }
            list.add(item.toUI())
        }
        return list
    }
}
