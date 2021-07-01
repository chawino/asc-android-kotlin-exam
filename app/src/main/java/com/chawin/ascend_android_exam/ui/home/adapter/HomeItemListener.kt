package com.chawin.ascend_android_exam.ui.home.adapter

import com.chawin.ascend_android_exam.domain.home.Product


interface HomeItemListener {
    fun onItemSelected(product : Product)
}
