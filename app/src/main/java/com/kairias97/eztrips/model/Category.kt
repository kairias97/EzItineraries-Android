package com.kairias97.eztrips.model

import com.google.gson.annotations.SerializedName
import com.kairias97.eztrips.data.local.AppDatabase
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.structure.BaseModel


@Table(name = "category", database = AppDatabase::class,
        allFields = true)
class Category : BaseModel(){

    @PrimaryKey
    @Column(name = "id")
    @SerializedName("id")
    var id : Int? = null

    @NotNull
    @Column(name ="name")
    @SerializedName("name")
    var name : String? = null

}