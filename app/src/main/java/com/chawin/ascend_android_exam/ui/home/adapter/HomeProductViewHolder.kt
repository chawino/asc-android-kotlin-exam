package com.chawin.ascend_android_exam.ui.home.adapter

import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chawin.ascend_android_exam.R
import com.chawin.ascend_android_exam.databinding.HomeProductItemBinding
import com.chawin.ascend_android_exam.ext.toAmountFormat2Digit
import com.chawin.ascend_android_exam.ui.home.HomeInfo

class HomeProductViewHolder(private val itemBinding: HomeProductItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: HomeInfo, recyclerItemListener: HomeItemListener) {
//        ViewCompat.setTransitionName(itemBinding.ivProductPhoto, "photo_${item.id}")
        if (item.isNewProduct) {
            itemBinding.tvProductNewTag.visibility = View.VISIBLE
        } else {
            itemBinding.tvProductNewTag.visibility = View.INVISIBLE
        }
        Glide.with(itemBinding.ivProductPhoto)
            .load(item.image)
            .placeholder(R.drawable.ic_default_image)
            .fallback(R.drawable.ic_default_image)
            .error(R.drawable.ic_default_image)
            .into(itemBinding.ivProductPhoto)


        itemBinding.tvProductName.text = item.title
        itemBinding.tvProductPrice.text = item.price.toAmountFormat2Digit()

        itemBinding.dessertCard.setOnClickListener {
            ViewCompat.setTransitionName(itemBinding.ivProductPhoto, "test")
            item.id?.let {
                recyclerItemListener.onItemSelected(it)
            }

//            val directions = HomeFragmentDirections.actionHomeFragmentToDetailFragment(item)
//            val extra = FragmentNavigatorExtras(
//                itemBinding.ivProductPhoto to "photo_${item.id}")
//            it.findNavController().navigate(directions, extra)
        }
    }
}

