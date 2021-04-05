package com.gmail.uia059466.test_for_work_db

import android.content.Context
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource

object ServiceLocator {

    private var database: LocalDataSource? = null

    fun provideListDataSource(context: Context): LocalDataSource {
        synchronized(this) {

            return database ?: createDataBase(context)
        }
    }

    private fun createDataBase(context: Context): LocalDataSource {
        synchronized(this) {
            val result = LocalDataSource(context)

            database = result
            return result
        }
    }
}
