package com.kairias97.ezitineraries.di

import com.kairias97.ezitineraries.ui.activities.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(SplashModule::class)])
    abstract  fun splashActivity() : SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(MainModule::class)])
    abstract  fun mainActivity() : MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(NewTripPlanModule::class)])
    abstract  fun newTripPlanActivity() : NewTripPlanActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(TripPlanPreferencesConfigurationModule::class)])
    abstract  fun tripPlanPreferencesConfigurationActivity() : TripPlanPreferencesConfigurationActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(TripPlanAttractionsVisualizationModule::class)])
    abstract  fun tripPlanAttractionsActivity() : TripPlanAttractionsActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(TripPlanDetailModule::class)])
    abstract  fun tripPlanDetailActivity() : TripPlanDetailActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(TripPlanSavedPreferencesModule::class)])
    abstract  fun tripPlanSavedPreferencesActivity() : TripPlanSavedPreferencesActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(ItineraryDetailModule::class)])
    abstract  fun itineraryDetailActivity() : ItineraryDetailActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(ItineraryPreferencesModule::class)])
    abstract  fun itineraryPreferencesActivity() : ItineraryPreferencesActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(ItineraryRouteModule::class)])
    abstract  fun itineraryRouteActivity() : ItineraryRouteActivity



}