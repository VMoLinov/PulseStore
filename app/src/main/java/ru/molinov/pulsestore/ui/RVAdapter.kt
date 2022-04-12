package ru.molinov.pulsestore.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.molinov.pulsestore.R
import ru.molinov.pulsestore.databinding.RecyclerItemContentBinding
import ru.molinov.pulsestore.databinding.RecyclerItemHeaderBinding
import ru.molinov.pulsestore.model.Header
import ru.molinov.pulsestore.model.Items
import ru.molinov.pulsestore.model.StoreUI

class RVAdapter<T : Items> : ListAdapter<Items, RVAdapter<T>.BaseViewHolder>(DiffCallback()) {

    private var isGreen = true

    private class DiffCallback : DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean =
            oldItem.time == newItem.time

        override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean =
            oldItem.time == newItem.time
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
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
        holderBase.bind(currentList[position])

    override fun getItemCount(): Int = currentList.size

    abstract inner class BaseViewHolder(binding: View) :
        RecyclerView.ViewHolder(binding) {
        abstract fun bind(store: Items)
    }

    inner class HeaderViewHolder(private val binding: RecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(store: Items) = with(binding) {
            store as Header
            time.text = store.time
        }
    }

    inner class ItemViewHolder(private val binding: RecyclerItemContentBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(store: Items) = with(binding) {
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

        private fun LinearLayoutCompat.background(drawable: Int) {
            background = ResourcesCompat.getDrawable(resources, drawable, context.theme)
            isGreen = !isGreen
        }
    }

    companion object {
        const val HEADER = 0
        const val STORE = 1
    }
}
