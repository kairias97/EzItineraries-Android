package com.kairias97.eztrips.model

import com.kairias97.eztrips.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(name = "trip_preference", database = AppDatabase::class,
        allFields = true)
class TouristTripPreference : BaseModel(){
    @PrimaryKey(autoincrement = true)
    @Column(name = "id")
    var id : Int = 0

    @NotNull
    @Column(name = "weight")
    var assignedWeight : Double? = null

    @ForeignKey(tableClass = TripPlan::class,
            references = [(ForeignKeyReference(columnName = "trip_plan_id",
                    foreignKeyColumnName = "id"))],
            onDelete = ForeignKeyAction.CASCADE)
    var tripPlanId : Int? = null

    @ForeignKey(tableClass = Category::class,
            saveForeignKeyModel = true,
            references = [(ForeignKeyReference(columnName = "category_id",
                    foreignKeyColumnName = "id"))],
            onDelete = ForeignKeyAction.CASCADE)
    var category: Category? = null


}