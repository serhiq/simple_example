package com.gmail.uia059466.test_for_work_db.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.gmail.uia059466.test_for_work_db.account.accountedit.IconAccount
import com.gmail.uia059466.test_for_work_db.db.account.AccountTable
import com.gmail.uia059466.test_for_work_db.db.currency.UserCurrencyTable
import com.gmail.uia059466.test_for_work_db.db.rates.UserRateTable
import com.gmail.uia059466.test_for_work_db.db.transaction.TransactionTable

class AppDatabaseHelper private constructor(
        context: Context
) : SQLiteOpenHelper(context.applicationContext, "app.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        with(db) {
            execSQL(UserCurrencyTable.CREATE)
            execSQL(UserRateTable.CREATE)
            execSQL(AccountTable.CREATE)
            execSQL(TransactionTable.CREATE)
            fillAccountAndCurrencies(db)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) = Unit

    private fun fillAccountAndCurrencies(db: SQLiteDatabase) {
        db.transact {
            val values = ContentValues() // reduce, reuse
            values.put(UserCurrencyTable.COLUMN_CODE_CURRENCY, "RUB")
            db.insert(UserCurrencyTable.TABLE_NAME, null, values)

            val values2 = ContentValues() // reduce, reuse
            values2.put(UserCurrencyTable.COLUMN_CODE_CURRENCY, "EUR")
            db.insert(UserCurrencyTable.TABLE_NAME, null, values2)

            val valuesAccountByDefault = ContentValues()

            values2.put(AccountTable.COLUMN_TITLE, "Наличные")
            values2.put(AccountTable.COLUMN_CODE_ICON, IconAccount.CASH.code)
            values2.put(AccountTable.COLUMN_AMOUNT, 0)
            db.insert(AccountTable.TABLE_NAME, null, valuesAccountByDefault)
        }
    }

    fun reset() {
        with(writableDatabase) {
            delete(UserCurrencyTable.TABLE_NAME, null, null)
            delete(UserCurrencyTable.TABLE_NAME, null, null)
            delete(UserRateTable.TABLE_NAME, null, null)
            delete(AccountTable.TABLE_NAME, null, null)
            delete(TransactionTable.TABLE_NAME, null, null)

            fillAccountAndCurrencies(this)
        }
    }

       companion object {

        private var _instance: AppDatabaseHelper? = null

        fun getInstance(context: Context): AppDatabaseHelper {
            return _instance ?: synchronized(AppDatabaseHelper::class) {
                AppDatabaseHelper(context).also { _instance = it }
            }
        }
    }
}


inline fun SQLiteDatabase.transact(transaction: SQLiteDatabase.() -> Unit) {
    try {
        beginTransaction()
        transaction()
        setTransactionSuccessful()
    } finally {
        endTransaction()
    }
}
