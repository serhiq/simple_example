package com.gmail.uia059466.test_for_work_db.db.account

import android.provider.BaseColumns

object AccountTable {

    const val TABLE_NAME = "accounts"

    const val COLUMN_ID = BaseColumns._ID
    const val COLUMN_TITLE = "title"
    const val COLUMN_CODE_ICON = "code_icon"
    const val COLUMN_AMOUNT = "amount"


    @JvmField val CREATE = """CREATE TABLE $TABLE_NAME (
                                $COLUMN_ID  INTEGER PRIMARY KEY AUTOINCREMENT, 
                                $COLUMN_TITLE TEXT NOT NULL, 
                                $COLUMN_CODE_ICON TEXT NOT NULL, 
                                $COLUMN_AMOUNT INTEGER NOT NULL );"""
}
