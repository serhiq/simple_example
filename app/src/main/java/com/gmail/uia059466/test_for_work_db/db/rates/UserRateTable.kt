package com.gmail.uia059466.test_for_work_db.db.rates

import android.provider.BaseColumns


object UserRateTable {

    const val TABLE_NAME = "user_rate"

    const val COLUMN_ID = BaseColumns._ID
    const val COLUMN_CODE_CURRENCY = "code_currency"
    const val COLUMN_CODE_RATE = "rate"
    const val COLUMN_CODE_DATE = "date"

    const val CREATE = """CREATE TABLE $TABLE_NAME (
                                $COLUMN_ID  INTEGER PRIMARY KEY AUTOINCREMENT, 
                                $COLUMN_CODE_CURRENCY TEXT NOT NULL, 
                                $COLUMN_CODE_RATE TEXT NOT NULL, 
                                $COLUMN_CODE_DATE INTEGER NOT NULL );"""
}
