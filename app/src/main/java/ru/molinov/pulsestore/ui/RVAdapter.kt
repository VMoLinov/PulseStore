package ru.molinov.pulsestore.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import ru.molinov.pulsestore.R
import ru.molinov.pulsestore.databinding.RecyclerItemContentBinding
import ru.molinov.pulsestore.databinding.RecyclerItemHeaderBinding
import ru.molinov.pulsestore.model.Header
import ru.molinov.pulsestore.model.Items
import ru.molinov.pulsestore.model.StoreUI

class RVAdapter<T : Items> : RecyclerView.Adapter<RVAdapter<T>.BaseViewHolder>() {
    private var data = listOf<T>()
    private var isGreen = true

    @SuppressLint("NotifyDataSetChanged")
    fun setStore(store: List<T>) {
        data = store
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is Header -> HEADER
            is StoreUI -> STORE
            else -> throw IllegalStateException()
        }
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
            STORE -> ItemViewHolder(
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
        abstract fun bind(store: T)
    }

    inner class HeaderViewHolder(private val binding: RecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(store: T) = with(binding) {
            store as Header
            time.text = store.time
        }
    }

    inner class ItemViewHolder(private val binding: RecyclerItemContentBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(store: T) = with(binding) {
            store as StoreUI
            root.background(
                if (isGreen) R.drawable.content_item_yellow
                else R.drawable.content_item_green
            )
            time.text = store.time
            systolic.text = store.systolic
            dystolic.text = store.dystolic
            pulse.text = store.pulse
        }

        private fun LinearLayoutCompat.background(id: Int) {
            background = ResourcesCompat.getDrawable(resources, id, context.theme)
            isGreen = !isGreen
        }
    }

    companion object {
        const val HEADER = 0
        const val STORE = 1
    }
}
