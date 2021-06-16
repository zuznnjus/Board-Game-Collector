package com.example.boardgamecollector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.boardgamecollector.R
import com.example.boardgamecollector.data.BoardGame
import com.example.boardgamecollector.data.Ranking

class BoardGameRankingAdapter(private val context: Context,
                              private val list: List<Ranking>,
                              private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var textViewDate: TextView = itemView.findViewById(R.id.textViewRankingActivityDateId)
        internal var textViewPosition: TextView = itemView.findViewById(R.id.textViewRankingActivityPositionId)

        internal fun bind(position: Int) {
            textViewDate.text = list[position].date.toString()
            textViewPosition.text = list[position].rankingPosition.toString()
            textViewPosition.setOnClickListener(this)
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
            LayoutInflater.from(context).inflate(R.layout.ranking_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener{
        fun onItemClick(ranking: Ranking)
    }
}