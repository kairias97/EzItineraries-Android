package com.kairias97.ezitineraries.data.local

import com.raizlabs.android.dbflow.annotation.Database

@Database(version = AppDatabase.VERSION,
        foreignKeyConstraintsEnforced = true,
        name = AppDatabase.NAME)
object AppDatabase {
    const val VERSION = 1
    const val NAME = "EzItinerariesDb"
}