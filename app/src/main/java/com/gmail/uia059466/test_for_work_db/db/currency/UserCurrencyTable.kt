package com.gmail.uia059466.test_for_work_db.db.currency

import android.provider.BaseColumns

object UserCurrencyTable {

    const val TABLE_NAME = "user_currency"

    const val COLUMN_ID = BaseColumns._ID
    const val COLUMN_CODE_CURRENCY = "code_currency"

    @JvmField
    val CREATE = """CREATE TABLE $TABLE_NAME ($COLUMN_ID  INTEGER PRIMARY KEY AUTOINCREMENT, 
                                $COLUMN_CODE_CURRENCY TEXT NOT NULL);"""
}
