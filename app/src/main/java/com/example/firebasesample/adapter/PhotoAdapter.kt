package com.example.firebasesample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasesample.R
import com.example.firebasesample.databinding.ItemPhotoBinding
import com.example.firebasesample.model.ContentDTO

class PhotoAdapter : RecyclerView.Adapter<PhotoViewHolder>() {

    private val contentList = mutableListOf<ContentDTO>()

    private lateinit var itemClickListener: (item: ContentDTO) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(parent)

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(contentList[position], itemClickListener)
    }

    override fun getItemCount(): Int =
        contentList.size


    fun addAll(list: List<ContentDTO>) {
        contentList.addAll(list)
        notifyDataSetChanged()
    }

    fun add(contentDTO: ContentDTO) {
        contentList.add(contentDTO)
        notifyItemChanged(contentList.lastIndex)
    }

    fun clear() {
        contentList.clear()
        notifyDataSetChanged()
    }

    fun setItemClickListener(itemClickListener: (item: ContentDTO) -> Unit) {
        this.itemClickListener = itemClickListener
    }
}


class PhotoViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
) {

    private val binding = ItemPhotoBinding.bind(itemView)

    fun bind(item: ContentDTO, itemClickListener: (item: ContentDTO) -> Unit) {

        itemView.setOnClickListener {
            itemClickListener(item)
        }

        item.imageUrl?.let {
            Glide.with(itemView.context).load(it).into(binding.image)
        }
    }

}