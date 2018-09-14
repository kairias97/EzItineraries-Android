package com.kairias97.eztrips.model

import com.google.gson.annotations.SerializedName
import com.kairias97.eztrips.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.NotNull
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

@Table(name = "country", database = AppDatabase::class)
 class Country() : BaseModel() {
    @Column(name = "iso_numeric_code")
    @PrimaryKey
    @SerializedName("isoNumericCode")
    var isoNumericCode : String? = null

   @NotNull
    @Column(name = "name")
    @SerializedName("name")
    var name: String? = null

   @NotNull
    @Column(name = "alpha2_code")
    @SerializedName("alpha2Code")
    var alpha2Code : String? = null

   @NotNull
    @Column(name = "alpha3_code")
    @SerializedName("alpha3Code")
    var alpha3Code : String? = null
 }