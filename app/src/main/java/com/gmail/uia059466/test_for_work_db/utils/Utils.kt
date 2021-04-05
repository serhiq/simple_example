package com.gmail.uia059466.test_for_work_db.utils

import android.widget.EditText
import java.util.*

fun text(text: EditText): String? {
    val s = text.text.toString().trim { it <= ' ' }
    return if (s.isNotEmpty()) s else null
}

fun toBeginDay(date: Date): Date {
    val cal: Calendar = Calendar.getInstance()
    cal.time = date
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.getTime()
}

fun toEndDay(date: Date): Date {
    val cal: Calendar = Calendar.getInstance()
    cal.setTime(date)
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.MILLISECOND, 999)
    return cal.getTime()
}