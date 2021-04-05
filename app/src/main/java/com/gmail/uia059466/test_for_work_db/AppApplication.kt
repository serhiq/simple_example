package com.gmail.uia059466.test_for_work_db

import android.app.Application
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource

class AppApplication : Application() {

    val localDataSource: LocalDataSource
        get() = ServiceLocator.provideListDataSource(this)

}