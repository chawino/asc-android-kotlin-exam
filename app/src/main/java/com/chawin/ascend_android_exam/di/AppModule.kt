package com.chawin.ascend_android_exam.di

import com.chawin.ascend_android_exam.data.home.DetailRepositoryImpl
import com.chawin.ascend_android_exam.data.home.HomeRepositoryImpl
import com.chawin.ascend_android_exam.domain.detail.DetailRepository
import com.chawin.ascend_android_exam.domain.home.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun bindDetailRepository(impl: DetailRepositoryImpl): DetailRepository
}

