package com.gmail.uia059466.test_for_work_db.utils

import android.app.Application

object InjectorUtils {
    fun provideViewModelFactory(application: Application): ViewModelFactory {
        return ViewModelFactory(application)
    }
}