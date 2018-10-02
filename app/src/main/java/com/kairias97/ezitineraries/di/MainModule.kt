package com.kairias97.ezitineraries.di

import com.kairias97.ezitineraries.presenter.IItinerariesAdminPresenter
import com.kairias97.ezitineraries.presenter.ITouristAttractionSuggestionPresenter
import com.kairias97.ezitineraries.presenter.ITripPlansAdminPresenter
import com.kairias97.ezitineraries.presenter.implementations.ItinerariesAdminPresenter
import com.kairias97.ezitineraries.presenter.implementations.TouristAttractionSuggestionPresenter
import com.kairias97.ezitineraries.presenter.implementations.TripPlansAdminPresenter
import com.kairias97.ezitineraries.ui.fragments.ItinerariesFragment
import com.kairias97.ezitineraries.ui.fragments.SuggestAttractionFragment
import com.kairias97.ezitineraries.ui.fragments.TripPlansFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {
    @FragmentScoped
    @ContributesAndroidInjector

    abstract fun  tripPlansFragment() : TripPlansFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun  itinerariesFragment() : ItinerariesFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun  suggestAttractionFragment() : SuggestAttractionFragment

    @ActivityScoped
    @Binds
    abstract  fun tripPlansAdminPresenter(presenter: TripPlansAdminPresenter)
            : ITripPlansAdminPresenter
    @ActivityScoped
    @Binds
    abstract  fun itinerariesAdminPresenter(presenter: ItinerariesAdminPresenter)
            : IItinerariesAdminPresenter
    @ActivityScoped
    @Binds
    abstract  fun suggestAttractionPresenter(presenter: TouristAttractionSuggestionPresenter)
            : ITouristAttractionSuggestionPresenter


}