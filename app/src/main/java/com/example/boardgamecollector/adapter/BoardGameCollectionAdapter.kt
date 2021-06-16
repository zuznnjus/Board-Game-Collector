package com.example.boardgamecollector.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.boardgamecollector.R
import com.example.boardgamecollector.data.BoardGame
import com.squareup.picasso.Picasso

class BoardGameCollectionAdapter(
    private val context: Context,
    private val list: List<BoardGame>,
    private val listener: OnItemClickListener,
    private val deleteListener: (BoardGame) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var textViewRanking: TextView = itemView.findViewById(R.id.textViewListRankingId)
        internal var imageViewThumbnail: ImageView =
            itemView.findViewById(R.id.imageViewListThumbnailId)
        internal var textViewDescription: TextView =
            itemView.findViewById(R.id.textViewListGameDescriptionId)
        val deleteButton: ImageButton = itemView.findViewById(R.id.imageButton)

        internal fun bind(position: Int) {
            if (list[position].bggId != 0) {
                textViewRanking.text = list[position].ranking.toString()
            }

            textViewDescription.text = list[position].toString()

            if (!list[position].thumbnail.isNullOrBlank()) {
                Picasso.get().load(list[position].thumbnail).into(imageViewThumbnail);
            }

            textViewDescription.setOnClickListener(this)
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
            LayoutInflater.from(context)
                .inflate(R.layout.board_games_list_with_images, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(game: BoardGame)
    }
}