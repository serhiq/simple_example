package com.gmail.uia059466.test_for_work_db.utils

import android.graphics.Color
import android.view.View
import androidx.annotation.AttrRes

fun View.getThemeColor(@AttrRes attrResId: Int): Int {
    val a = context.obtainStyledAttributes(null, intArrayOf(attrResId))
    try {
        return a.getColor(0, Color.MAGENTA)
    } finally {
        a.recycle()
    }
}