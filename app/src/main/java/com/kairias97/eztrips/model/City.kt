package com.kairias97.eztrips.model

import com.google.gson.annotations.SerializedName
import com.kairias97.eztrips.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(name = "city", database = AppDatabase::class,
        allFields = true)
class City() : BaseModel(){
    @PrimaryKey
    @Column(name = "id")
    @SerializedName("cityId")
    var cityId : Int? = null

    @NotNull
    @Column(name = "name")
    @SerializedName("name")
    var name: String? = ""


    @SerializedName("country")
    @ForeignKey(saveForeignKeyModel = true,
            tableClass = Country::class,
            references = [(ForeignKeyReference(columnName = "country_id",
                    foreignKeyColumnName = "iso_numeric_code"))],
            onDelete = ForeignKeyAction.CASCADE)
    var country: Country? = null
}