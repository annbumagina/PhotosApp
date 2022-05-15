package com.annbumagina.photosapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(val onClick: (Int) -> Any?): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var items: ArrayList<String?> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val view = inflater.inflate(R.layout.list_item, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun clear() {
        if (items.size > 0) {
            val old = items.size
            items.clear()
            notifyItemRangeRemoved(0, old)
        }
    }

    fun setElement(p0: Int, p1: String) {
        items.add(p1)
        notifyItemInserted(p0)
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        if (items[p1] == null) return
        p0.textView.text = items[p1]
        p0.textView.setOnClickListener {
            onClick(p1)
        }
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }
}