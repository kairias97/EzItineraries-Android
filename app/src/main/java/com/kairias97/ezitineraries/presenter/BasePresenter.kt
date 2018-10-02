package com.kairias97.ezitineraries.presenter

interface BasePresenter<T> {
    fun takeView(view : T)
    fun dropView()
}