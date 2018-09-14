package com.kairias97.eztrips.model

import com.kairias97.eztrips.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(name = "itinerary_tourist_preference", database = AppDatabase::class,
        allFields = true)
class ItineraryTouristPreference : BaseModel(){
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    var id : Int = 0

    @NotNull
    @Column(name = "weight")
    var weight : Double? = null

    @ForeignKey(saveForeignKeyModel = true,
            tableClass = Category::class,
            onDelete = ForeignKeyAction.CASCADE,
            references = [(ForeignKeyReference(columnName = "category_id",
                    foreignKeyColumnName = "id"))])
    var category: Category? = null


    @ForeignKey(tableClass = CalculatedItinerary::class,
            references = [(ForeignKeyReference(columnName = "calculated_itinerary_id",
                    foreignKeyColumnName = "id"))],
            onDelete = ForeignKeyAction.CASCADE)
    var calculatedItineraryId : Int? = null


}