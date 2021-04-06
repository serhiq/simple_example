package com.gmail.uia059466.test_for_work_db

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource

object ServiceLocator {

    private val lock = Any()
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

    @VisibleForTesting
    fun resetDatabase() {
        synchronized(lock) {
            database?.reset()
            database = null
        }
    }
}
