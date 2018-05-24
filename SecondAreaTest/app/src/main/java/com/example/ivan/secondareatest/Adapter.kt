package com.example.ivan.secondareatest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ivan.secondareatest.example.*

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private val list = listOf(MarksMapActivity::class.java,
            SneakMapActivity::class.java,
            KmShowMapsActivity::class.java,
            CircleRotateMapActivity::class.java,
            HeatMapActivity::class.java,
            MapViewActivity::class.java,
            MarksMapActivity::class.java)
    var callback:OnItemClick? = null

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.container.setOnClickListener({
            callback?.onItemClick(list[position])
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
                parent,
                false)
        return ViewHolder(view)
    }

    class ViewHolder(view: View?) : RecyclerView.ViewHolder(view) {
        val activityLabel = view?.findViewById<TextView>(R.id.activity_label)
        val container:android.support.constraint.ConstraintLayout =
                view?.findViewById(R.id.container)!!

        fun bind(item: Class<out Any>) {
            activityLabel?.text = item.simpleName

        }
    }

    interface OnItemClick{
        fun onItemClick(Class: Class<out Any>)
    }
}