package com.gmail.uia059466.test_for_work_db.utls

import android.app.Application
import com.gmail.uia059466.test_for_work_db.utls.ViewModelFactory

object InjectorUtils {
    fun provideViewModelFactory(application: Application): ViewModelFactory {
        return ViewModelFactory(application)
    }
}