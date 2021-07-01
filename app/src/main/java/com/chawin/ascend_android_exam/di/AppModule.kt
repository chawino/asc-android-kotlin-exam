package com.chawin.ascend_android_exam.di

import com.chawin.ascend_android_exam.data.home.HomeRepositoryImpl
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
    abstract fun bindRepository(impl: HomeRepositoryImpl): HomeRepository
}

