package com.kairias97.ezitineraries.data.local

import com.kairias97.ezitineraries.data.OperationWithNoResultCallback
import com.kairias97.ezitineraries.data.OperationWithResultCallback
import com.kairias97.ezitineraries.model.*
import com.raizlabs.android.dbflow.config.DatabaseDefinition
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction
import javax.inject.Inject
import javax.inject.Singleton
import com.raizlabs.android.dbflow.sql.language.Join
import com.raizlabs.android.dbflow.sql.language.NameAlias
import com.raizlabs.android.dbflow.sql.language.Select
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper


@Singleton
class LocalRepository @Inject constructor(val databaseDefinition: DatabaseDefinition): TripPlanDAO, CalculatedItineraryDAO, TouristAttractionDAO {


    override fun saveTripPlan(tripPlan: TripPlan, callback: OperationWithNoResultCallback?) {
        var transaction = databaseDefinition.beginTransactionAsync {
            tripPlan.save(it)
        }.success {
            callback?.onSuccess()
        }.error { tran, error ->
            callback?.onError()
        }.build()

        transaction.execute()

    }

    override fun deleteTripPlanById(tripPlanId: Int, callback: OperationWithNoResultCallback?) {
        var transaction = databaseDefinition.beginTransactionAsync {
            SQLite.delete(TripPlan::class.java)
                    .where(TripPlan_Table.id.eq(tripPlanId))
                    .execute(it)
        }.success {
            callback?.onSuccess()
        }.error { tran, error ->
            callback?.onError()
        }.build()

        transaction.execute()
    }

    override fun getTripPlanById(tripPlanId: Int, callback: OperationWithResultCallback<TripPlan>?) {
        SQLite.select()
                .from(TripPlan::class.java)
                .where(TripPlan_Table.id.eq(tripPlanId))
                .async()
                .error { transaction, error ->
                    callback?.onError()
                }
                .querySingleResultCallback { transaction, tResult ->
                    callback?.onSuccess(tResult!!)
                }.execute()
    }

    override fun getTripPlansHeaders(callback: OperationWithResultCallback<List<TripPlanHeader>>?) {
        SQLite.select(TripPlan_Table.id
                .withTable(NameAlias.builder("tp").build())
                .`as`("trip_plan_id"),
                TripPlan_Table.tourist_visit_date
                        .withTable(NameAlias.builder("tp").build())
                        .`as`("visit_date"),
                TripPlan_Table.name
                        .withTable(NameAlias.builder("tp").build())
                        .`as`("name"),
                City_Table.name
                        .withTable(NameAlias.builder("ci").build())
                        .`as`("city"),
                Country_Table.name
                        .withTable(NameAlias.builder("co").build())
                        .`as`("country")
                )
                .from(TripPlan::class.java)
                .`as`("tp")
                .join(City::class.java, Join.JoinType.INNER)
                .`as`("ci")
                .on(TripPlan_Table.city_id
                        .withTable(NameAlias.builder("tp").build())
                        .eq(City_Table.id.withTable(NameAlias.builder("ci").build())))
                .join(Country::class.java, Join.JoinType.INNER)
                .`as`("co")
                .on(City_Table.country_id
                        .withTable(NameAlias.builder("ci").build())
                        .eq(Country_Table.iso_numeric_code.withTable(NameAlias.builder("co").build())
                        ))
                .orderBy(TripPlan_Table.tourist_visit_date, false)
                .async().error { transaction, error ->
                    callback?.onError()
                }
                .queryResultCallback { transaction, tResult ->

                    var tripPlanHeaders = tResult.toCustomListClose(TripPlanHeader::class.java)
                    callback?.onSuccess(tripPlanHeaders)
                }.execute()


    }

    override fun saveItinerary(itinerary: CalculatedItinerary, callback: OperationWithNoResultCallback?) {
        var transaction = databaseDefinition.beginTransactionAsync {
            itinerary.save(it)
        }.success {
            callback?.onSuccess()
        }.error { tran, error ->
            callback?.onError()
        }.build()

        transaction.execute()
    }

    override fun deleteItineraryById(itineraryId: Int, callback: OperationWithNoResultCallback?) {
        var transaction = databaseDefinition.beginTransactionAsync {
            SQLite.delete(CalculatedItinerary::class.java)
                    .where(CalculatedItinerary_Table.id.eq(itineraryId))
                    .execute(it)
        }.success {
            callback?.onSuccess()
        }.error { tran, error ->
            callback?.onError()
        }.build()

        transaction.execute()
    }

    override fun getItineraryById(itineraryId: Int, callback: OperationWithResultCallback<CalculatedItinerary>?) {

        SQLite.select()
                .from(CalculatedItinerary::class.java)
                .where(CalculatedItinerary_Table.id.eq(itineraryId))
                .async()
                .error { transaction, error ->
                    callback?.onError()
                }
                .querySingleResultCallback { transaction, tResult ->
                    callback?.onSuccess(tResult!!)
                }.execute()
    }

    override fun getItinerariesHeaders(callback: OperationWithResultCallback<List<CalculatedItineraryHeader>>?) {
        SQLite.select(CalculatedItinerary_Table.id
                .withTable(NameAlias.builder("cit").build())
                .`as`("calculated_itinerary_id"),
                CalculatedItinerary_Table.score
                        .withTable(NameAlias.builder("cit").build())
                        .`as`("score"),
                CalculatedItinerary_Table.visit_date
                        .withTable(NameAlias.builder("cit").build())
                        .`as`("visit_date"),
                City_Table.name
                        .withTable(NameAlias.builder("ci").build())
                        .`as`("city"),
                Country_Table.name
                        .withTable(NameAlias.builder("co").build())
                        .`as`("country")
                )
                .from(CalculatedItinerary::class.java)
                .`as`("cit")
                .join(City::class.java, Join.JoinType.INNER)
                .`as`("ci")
                .on(CalculatedItinerary_Table.city_id
                        .withTable(NameAlias.builder("cit").build())
                        .eq(City_Table.id.withTable(NameAlias.builder("ci").build())))
                .join(Country::class.java, Join.JoinType.INNER)
                .`as`("co")
                .on(City_Table.country_id
                        .withTable(NameAlias.builder("ci").build())
                        .eq(Country_Table.iso_numeric_code
                                .withTable(NameAlias.builder("co").build()))
                )
                .orderBy(CalculatedItinerary_Table.visit_date, false)
                .async().error { transaction, error ->
                    callback?.onError()
                }
                .queryResultCallback { transaction, tResult ->
                    var itinerariesHeaders = tResult.toCustomListClose(CalculatedItineraryHeader::class.java)
                    callback?.onSuccess(itinerariesHeaders)
                }.execute()

    }

    override fun saveAll(attractions: List<TouristAttraction>, callback: OperationWithNoResultCallback?) {
        val processModelTransaction = ProcessModelTransaction.Builder<TouristAttraction>(ProcessModelTransaction
                .ProcessModel<TouristAttraction> { model, wrapper -> model?.save(wrapper!!) })
                .processListener { current, total, modifiedModel ->  }.addAll(attractions).build()

        val transaction = databaseDefinition
                .beginTransactionAsync(processModelTransaction)
                .success {
                    callback?.onSuccess()
                }.error { tran, error ->
                    callback?.onError()
                }
                .build()
        transaction.execute()
    }

    override fun getAttractionsByCity(cityId: Int, callback: OperationWithResultCallback<List<TouristAttraction>>?) {
        SQLite.select()
                .from(TouristAttraction::class.java)
                .where(TouristAttraction_Table.city_id.eq(cityId))
                .and(TouristAttraction_Table.active.eq(true))
                .async()
                .error { transaction, error ->
                    callback?.onError()
                }
                .queryListResultCallback { transaction, tResult ->
                    callback?.onSuccess(tResult!!)
                }.execute()
    }

}