package com.example.firebasesample.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasesample.data.model.ArticleModel
import com.example.firebasesample.databinding.ItemArticleBinding
import java.text.SimpleDateFormat
import java.util.*

class ViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(articleModel: ArticleModel, onItemClicked: (ArticleModel) -> Unit) {
        val format = SimpleDateFormat("MM월 dd일")
        val date = Date(articleModel.createdAt)

        binding.titleTextView.text = articleModel.title
        binding.dateTextView.text = format.format(date).toString()
        binding.priceTextView.text = articleModel.price

        if (articleModel.imageUrl.isNotEmpty()) {
            Glide.with(binding.thumbnailImageView)
                .load(articleModel.imageUrl)
                .into(binding.thumbnailImageView)
        }

        binding.root.setOnClickListener {
            onItemClicked(articleModel)
        }


    }
}