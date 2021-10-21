package com.jodel.jodelchallenge.adapter.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Item, Holder : BaseViewHolder<Item>> : RecyclerView.Adapter<Holder>() {
    protected var items: List<Item> = listOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewId: Int
    ): Holder {
        return  viewHolder(
            LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false)
        )
    }

    abstract fun viewHolder(itemView: View): Holder

    abstract val layoutId: Int

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }
}