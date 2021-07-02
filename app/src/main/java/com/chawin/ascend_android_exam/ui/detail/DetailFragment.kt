package com.chawin.ascend_android_exam.ui.detail

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.chawin.ascend_android_exam.R
import com.chawin.ascend_android_exam.databinding.FragmentDetailBinding
import com.chawin.ascend_android_exam.ext.toAmountFormat2Digit
import com.chawin.ascend_android_exam.ui.home.HomeInfo
import com.chawin.ascend_android_exam.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(), LifecycleObserver {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
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
        viewModel.initialize(args.productId)
    }

    private fun observe() {
        viewModel.dialogError.observe(this, {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Oops")
                .setMessage(String.format(getString(R.string.dialog_error_message), it))
                .setCancelable(false)
                .setPositiveButton("Retry") { dialog, _ ->
                    viewModel.getProduct()
                    dialog.cancel()
                }
                .setNegativeButton("Close app") { dialog, _ ->
                    activity?.finish()
                    dialog.cancel()
                }
            dialogBuilder.create().show()
        })
        viewModel.loading.observe(this, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        viewModel.showEmptyLayout.observe(this, {
            if (it) {
                binding.noDataLayout.noDataLayoutContent.visibility = View.VISIBLE
            } else {
                binding.noDataLayout.noDataLayoutContent.visibility = View.GONE
            }
        })
        viewModel.product.observe(this, {
            it.apply {
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
        })
    }

}