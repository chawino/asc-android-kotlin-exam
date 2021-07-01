package com.chawin.ascend_android_exam.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chawin.ascend_android_exam.databinding.HomeProductItemBinding
import com.chawin.ascend_android_exam.databinding.HomeTitleItemBinding
import com.chawin.ascend_android_exam.domain.home.Product
import com.chawin.ascend_android_exam.ui.home.HomeViewModel

class HomeAdapter(private val homeViewModel: HomeViewModel, private val product: List<Product>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val onItemClickListener: HomeItemListener = object : HomeItemListener {
        override fun onItemSelected(product: Product) {
            homeViewModel.openDessertDetail(product)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            0 -> {
                val itemBinding =
                    HomeTitleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeTitleViewHolder(itemBinding)
            }
            else -> {
                val itemBinding =
                    HomeProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HomeProductViewHolder(itemBinding)
            }
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            0 -> {
                (holder as HomeTitleViewHolder)
            }
            else -> {
                (holder as HomeProductViewHolder).bind(product[position - 1], onItemClickListener)
            }
        }
//        if (position > 0) (holder as HomeProductViewHolder).bind(product[position], onItemClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return product.size + 1
    }
}

