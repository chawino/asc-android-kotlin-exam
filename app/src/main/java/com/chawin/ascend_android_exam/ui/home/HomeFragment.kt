package com.chawin.ascend_android_exam.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.GridLayoutManager
import com.chawin.ascend_android_exam.R
import com.chawin.ascend_android_exam.databinding.FragmentHomeBinding
import com.chawin.ascend_android_exam.ui.home.adapter.HomeAdapter
import com.chawin.ascend_android_exam.util.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), LifecycleObserver {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.recyclerView.apply {
            val layoutManager = (layoutManager as GridLayoutManager).setSpanSizeLookup(object :
                GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == 0)
                        2
                    else
                        1
                }
            })
            addItemDecoration(GridSpacingItemDecoration(2, 32, true))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProducts()
    }

    private fun observe() {
        viewModel.dialogError.observe(this, {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setMessage(String.format(getString(R.string.dialog_error_message), it))
                .setCancelable(false)
                .setPositiveButton("Retry") { dialog, _ ->
                    viewModel.getProducts()
                    dialog.cancel()
                }
                .setNegativeButton("Close app") { dialog, _ ->
                    activity?.finish()
                    dialog.cancel()
                }
            val alert = dialogBuilder.create()
            alert.setTitle(getString(R.string.dialog_error_title))
            alert.show()
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
        viewModel.product.observe(this, { homes ->
            adapter = HomeAdapter(viewModel, homes)
            binding.recyclerView.adapter = adapter
        })
        viewModel.navigateToProductDetail.observe(this, {

        })
    }
}