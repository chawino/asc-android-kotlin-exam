package com.chawin.ascend_android_exam.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chawin.ascend_android_exam.databinding.HomeProductItemBinding
import com.chawin.ascend_android_exam.domain.home.Product
import com.chawin.ascend_android_exam.ext.toAmountFormat2Digit

class HomeProductViewHolder(private val itemBinding: HomeProductItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: Product, recyclerItemListener: HomeItemListener) {
        if (item.isNewProduct) {
            itemBinding.tvProductNewTag.visibility = View.VISIBLE
        } else {
            itemBinding.tvProductNewTag.visibility = View.INVISIBLE
        }

        Glide.with(itemBinding.ivProductPhoto)
            .load(item.image)
            .into(itemBinding.ivProductPhoto)


        itemBinding.tvProductName.text = item.title
        itemBinding.tvProductPrice.text = item.price.toAmountFormat2Digit()
    }
}

