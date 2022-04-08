package ru.molinov.pulsestore

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.molinov.pulsestore.databinding.RecyclerItemContentBinding
import ru.molinov.pulsestore.databinding.RecyclerItemHeaderBinding
import ru.molinov.pulsestore.model.StoreUI

class RVAdapter : RecyclerView.Adapter<RVAdapter.BaseViewHolder>() {
    private var data = listOf<StoreUI>()

    @SuppressLint("NotifyDataSetChanged")
    fun setStore(store: List<StoreUI>) {
        data = store
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            HEADER -> HeaderViewHolder(
                RecyclerItemHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ITEM -> ItemViewHolder(
                RecyclerItemContentBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holderBase: BaseViewHolder, position: Int) =
        holderBase.bind(data[position])

    override fun getItemCount(): Int = data.size

    abstract inner class BaseViewHolder(binding: View) :
        RecyclerView.ViewHolder(binding) {
        abstract fun bind(store: StoreUI)
    }

    inner class HeaderViewHolder(private val binding: RecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(store: StoreUI) = with(binding) {
            time.text = store.time
        }
    }

    inner class ItemViewHolder(private val binding: RecyclerItemContentBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(store: StoreUI) = with(binding) {
            time.text = store.time
        }
    }

    companion object {
        const val HEADER = 1
        const val ITEM = 2
    }
}
