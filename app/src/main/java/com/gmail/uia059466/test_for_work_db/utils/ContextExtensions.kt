
package com.gmail.uia059466.test_for_work_db.utils

import android.content.Context
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource

fun Context.database() = LocalDataSource.getInstance(this)

