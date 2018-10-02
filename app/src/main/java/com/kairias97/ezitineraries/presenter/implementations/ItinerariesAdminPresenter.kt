package com.kairias97.ezitineraries.presenter.implementations

import com.kairias97.ezitineraries.data.ItinerariesRepository
import com.kairias97.ezitineraries.data.OperationWithNoResultCallback
import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.model.CalculatedItineraryHeader
import com.kairias97.ezitineraries.presenter.IItinerariesAdminPresenter
import com.kairias97.ezitineraries.view.IItinerariesAdminView
import javax.inject.Inject

class ItinerariesAdminPresenter @Inject constructor(
        private val mItinerariesRepository: ItinerariesRepository) : IItinerariesAdminPresenter {

    private var mItinerariesAdminView : IItinerariesAdminView ? = null

    override fun onViewItineraryRequest(itineraryHeader: CalculatedItineraryHeader) {
        mItinerariesAdminView?.navigateToItineraryVisualization(itineraryHeader.itineraryId!!)
    }

    override fun onItineraryRemovalRequest(itineraryHeader: CalculatedItineraryHeader) {
        mItinerariesRepository.deleteItineraryById(itineraryHeader.itineraryId!!,
                object:OperationWithNoResultCallback {
                    override fun onSuccess() {
                        mItinerariesAdminView?.removeItineraryHeaderFromUI(itineraryHeader)
                    }

                    override fun onError() {
                        mItinerariesAdminView?.showCommunicationErrorMessage()
                    }

                })
    }

    override fun provideItineraryHeaders() {
        mItinerariesRepository.getItinerariesHeaders(object:OperationWithResultCallback<List<CalculatedItineraryHeader>> {
            override fun onSuccess(result: List<CalculatedItineraryHeader>) {
                mItinerariesAdminView?.showItinerariesHeaders(result)
            }

            override fun onError() {
                mItinerariesAdminView?.showCommunicationErrorMessage()
            }

        })
    }

    override fun takeView(view: IItinerariesAdminView) {
        mItinerariesAdminView = view
    }

    override fun dropView() {
        mItinerariesAdminView = null
    }
}