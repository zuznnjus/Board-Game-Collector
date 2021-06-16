package com.example.boardgamecollector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.boardgamecollector.R
import com.example.boardgamecollector.data.Location

class LocationAdapter(
    private val context: Context,
    private val list: List<Location>,
    private val listener: OnItemClickListener,
    private val deleteListener: (Location) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var textViewLocationName: TextView =
            itemView.findViewById(R.id.textViewLocationNameId)
        val deleteButton: ImageButton = itemView.findViewById(R.id.imageButton2)

        internal fun bind(position: Int) {
            textViewLocationName.text = list[position].toString()
            textViewLocationName.setOnClickListener(this)
            deleteButton.setOnClickListener { deleteListener(list[position]) }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                list[position].let { listener.onItemClick(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.locations_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(location: Location)
    }
}