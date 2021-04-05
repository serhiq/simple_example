package com.gmail.uia059466.test_for_work_db.utls

import android.widget.EditText

fun text(text: EditText): String? {
    val s = text.text.toString().trim { it <= ' ' }
    return if (s.length > 0) s else null
}