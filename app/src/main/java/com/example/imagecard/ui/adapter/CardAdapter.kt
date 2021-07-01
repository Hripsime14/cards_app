package com.example.imagecard.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagecard.R
import com.example.imagecard.data.entity.CardEntity

class CardAdapter : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    private var titleClickListener: ((CardEntity?) -> Unit)? = null
    private var imageClickListener: ((CardEntity?) -> Unit)? = null
    private val diffCallback = object : DiffUtil.ItemCallback<CardEntity>() {
        override fun areItemsTheSame(oldItem: CardEntity, newItem: CardEntity): Boolean =
            oldItem.title == newItem.title && oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CardEntity, newItem: CardEntity): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    private var differ = AsyncListDiffer(this, diffCallback)
    var items: MutableList<CardEntity>
        set(value) = differ.submitList(value)
        get() = differ.currentList

    fun setTitleListener(titleClickListener: ((CardEntity?) -> Unit)?) {
        this.titleClickListener = titleClickListener
    }

    fun setImageListener(imageClickListener: ((CardEntity?) -> Unit)?) {
        this.imageClickListener = imageClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_row_card_recycler_view, parent, false)

        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val cardEntity = items[position]
        holder.bind(cardEntity)
    }

    override fun getItemCount() = items.size

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.card_image_view)
        private val title: EditText = view.findViewById(R.id.title_edit_text)


        init {
            image.setOnClickListener {
                imageClickListener?.invoke(items[adapterPosition])
            }

            // Define click listener for the ViewHolder's View.
            title.setOnEditorActionListener { v, actionId, event ->
                val cardEntity = items[adapterPosition]
                val txt = (v as EditText).text
                cardEntity.title = txt.toString()
                titleClickListener?.invoke(cardEntity)
                true
            }

        }

        fun bind(cardEntity: CardEntity) {
            title.setText(cardEntity.title)
            if(cardEntity.imagePath.isDigitsOnly()) {
                image.setImageResource(cardEntity.imagePath.toInt())
            } else {
                Glide.with(itemView.context)
                    .load(cardEntity.imagePath)
                    .placeholder(R.drawable.tree)
                    .into(image)
            }
        }
    }
}