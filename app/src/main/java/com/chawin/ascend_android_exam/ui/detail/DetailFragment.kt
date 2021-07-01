package com.chawin.ascend_android_exam.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.chawin.ascend_android_exam.R
import com.chawin.ascend_android_exam.databinding.FragmentDetailBinding
import com.chawin.ascend_android_exam.ext.toAmountFormat2Digit
import com.chawin.ascend_android_exam.ui.home.HomeInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(), LifecycleObserver {
    private lateinit var binding: FragmentDetailBinding

    private val args: DetailFragmentArgs by navArgs()

    private val homeInfo: HomeInfo by lazy {
        args.product
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeInfo.apply {
            binding.tvProductName.text = title
            binding.tvProductPrice.text = price.toAmountFormat2Digit()
            binding.tvProductDetail.text = content

            if (isNewProduct)
                binding.tvProductNewTag.visibility = View.VISIBLE
            else
                binding.tvProductNewTag.visibility = View.GONE

            Glide.with(binding.ivProductPhoto)
                .load(image)
                .placeholder(R.drawable.ic_default_image)
                .fallback(R.drawable.ic_default_image)
                .error(R.drawable.ic_default_image)
                .into(binding.ivProductPhoto)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("homeInfo", homeInfo)
    }

}