package com.kairias97.eztrips.model

import com.raizlabs.android.dbflow.annotation.*

class TouristVisit {
    @PrimaryKey(autoincrement = true)
    @Column(name="id")
    var id : Int = 0

    @NotNull
    @Column(name = "visit_order")
    var visitOrder : Int? = null

    @ForeignKey(onDelete = ForeignKeyAction.CASCADE,
            tableClass = TouristAttraction::class,
            saveForeignKeyModel = true,
            references = [(ForeignKeyReference(columnName = "tourist_attraction_id",
                    foreignKeyColumnName = "id"))]
            )
    var touristAttraction : TouristAttraction? = null


    @ForeignKey(tableClass = CalculatedItinerary::class,
            references = [(ForeignKeyReference(columnName = "calculated_itinerary_id",
                    foreignKeyColumnName = "id"))],
            onDelete = ForeignKeyAction.CASCADE)
    var calculatedItineraryId : Int? = null
}