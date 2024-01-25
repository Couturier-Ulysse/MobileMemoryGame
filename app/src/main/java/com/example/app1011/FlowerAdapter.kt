package com.example.app1011

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app1011.databinding.ActivityActionsBinding.inflate
import com.example.app1011.databinding.ActivityMainBinding

class FlowerAdapter(private val flowerDataSet:ArrayList<String>): RecyclerView.Adapter<FlowerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flower_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlowerAdapter.ViewHolder, position: Int) {
        holder.tvFlowerName.text = flowerDataSet[position]
    }

    override fun getItemCount() = flowerDataSet.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFlowerName: TextView = itemView.findViewById(R.id.tv_flower_name)
    }
}
