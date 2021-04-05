package com.gmail.uia059466.test_for_work_db.db.transaction

import android.provider.BaseColumns

object TransactionTable {

    const val TABLE_NAME = "transaction_table"

    const val COLUMN_ID = BaseColumns._ID
    const val COLUMN_CODE_TRANSACTION = "code_transaction"
    const val COLUMN_FROM_ACCOUNT_ID = "account_id"
    const val COLUMN_CODE_DATE = "date"
    const val COLUMN_CODE_NOTE = "note"
    const val COLUMN_CODE_AMOUNT = "amount"
    const val COLUMN_CODE_FROM_AMOUNT = "from_amount"
    const val COLUMN_CODE_FROM_CURRENCY = "from_currency"
    @JvmField val CREATE = """CREATE TABLE $TABLE_NAME (
                                $COLUMN_ID  INTEGER PRIMARY KEY AUTOINCREMENT, 
                                $COLUMN_CODE_TRANSACTION TEXT NOT NULL, 
                                $COLUMN_FROM_ACCOUNT_ID INTEGER NOT NULL, 
                                $COLUMN_CODE_DATE INTEGER NOT NULL, 
                                $COLUMN_CODE_NOTE TEXT NOT NULL, 
                                $COLUMN_CODE_AMOUNT TEXT NOT NULL, 
                                $COLUMN_CODE_FROM_AMOUNT TEXT NOT NULL, 
                                $COLUMN_CODE_FROM_CURRENCY TEXT NOT NULL);"""
}
