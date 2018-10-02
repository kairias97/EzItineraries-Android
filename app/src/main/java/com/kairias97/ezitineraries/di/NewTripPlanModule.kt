package com.kairias97.ezitineraries.di

import com.kairias97.ezitineraries.presenter.INewTripPlanPresenter
import com.kairias97.ezitineraries.presenter.implementations.NewTripPlanPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class NewTripPlanModule {

    @ActivityScoped
    @Binds
    abstract fun newTripPlanPresenter(presenter: NewTripPlanPresenter) : INewTripPlanPresenter
}