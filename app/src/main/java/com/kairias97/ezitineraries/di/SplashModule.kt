package com.kairias97.ezitineraries.di

import com.kairias97.ezitineraries.presenter.ISplashPresenter
import com.kairias97.ezitineraries.presenter.implementations.SplashPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class SplashModule {
    @ActivityScoped
    @Binds abstract fun splashPresenter(presenter: SplashPresenter)
            : ISplashPresenter
}
