package com.kairias97.eztrips.model

import com.google.gson.annotations.SerializedName
import com.kairias97.eztrips.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(name = "tourist_attraction", database = AppDatabase::class,
        allFields = true)
class TouristAttraction : BaseModel(){
    @SerializedName("id")
    @PrimaryKey
    @Column(name = "id")
    var id : Int? = null


    @SerializedName("name")
    @NotNull
    @Column(name = "name")
    var name : String? = null

    @SerializedName("address")
    @NotNull
    @Column(name = "address")
    var address : String? = null

    @SerializedName("googlePlaceId")
    @NotNull
    @Unique
    @Column(name = "google_place_id")
    var googlePlaceId : String? = null

    @SerializedName("phoneNumber")
    @Column(name="phone_number")
    var phoneNumber : String? = null

    @SerializedName("websiteUrl")
    @Column(name = "website_url")
    var websiteUrl : String? = null

    @SerializedName("rating")
    @Column(name = "rating")
    var rating : Double? = null

    @SerializedName("category")
    @ForeignKey(saveForeignKeyModel =  true,
            onDelete = ForeignKeyAction.CASCADE,
            tableClass = Category::class,
            references = [(ForeignKeyReference(columnName = "category_id", foreignKeyColumnName = "id"))])
    var category : Category? = null

    @SerializedName("geoposition")
    @ColumnMap
    var geolocation : Geolocation? = null

    @NotNull
    @SerializedName("isActive")
    @Column(name = "active", getterName = "isActive")
    var isActive: Boolean? = null

}