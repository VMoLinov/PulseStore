package ru.molinov.pulsestore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.molinov.pulsestore.model.StoreDB
import ru.molinov.pulsestore.model.StoreUI
import ru.molinov.pulsestore.remote.Network
import ru.molinov.pulsestore.remote.NetworkImpl

class MainViewModel(
    private val network: Network = NetworkImpl()
) : ViewModel() {

    var liveData: MutableLiveData<List<StoreUI>> = MutableLiveData()

    fun saveData(store: StoreDB) {
        Thread { network.saveData(store, ::callback) }.start()
    }

    fun readData() {
        Thread { network.readData(::callback) }.start()
    }

    private fun callback(data: List<StoreUI>) {
        liveData.value = data
    }
}
