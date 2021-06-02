package com.example.weatherproj.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherproj.R
import com.example.weatherproj.databaseobjects.TownClass

class TownsRecyclerViewAdapter : RecyclerView.Adapter<TownsRecyclerViewAdapter.ItemHolder>() {
    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val townTV: TextView = itemView.findViewById(R.id.townNameTv)
        private var town: TownClass? = null

        fun setData(city: TownClass) {
            town = city
            townTV.text = town?.name
        }

        init {
            townTV.setOnClickListener {
                town?.let {
                    onClick?.invoke(TownClass(it.name.toString()))
                }
            }
        }
    }

    var onClick: ( (TownClass) -> Unit)? = null

    private var containerList: ArrayList<TownClass>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.town_item, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return containerList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        containerList?.let {
            holder.setData(it[position])
        }
    }

    fun addItem(city: TownClass) {
        containerList?.let {
            val index = it.size
            it.add(city)
            notifyItemInserted(index)
            notifyItemRangeChanged(index, it.size)
        }
    }

    fun setItems(towns: List<TownClass>){
        containerList = (ArrayList(towns))
        notifyDataSetChanged()
    }
}