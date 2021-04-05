package com.gmail.uia059466.test_for_work_db.db.currency

import android.content.ContentValues
import com.gmail.uia059466.test_for_work_db.db.AppDatabaseHelper
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.db.IDbCommand
import com.gmail.uia059466.test_for_work_db.db.IResultDbCommand
import java.util.*

class AddUserCurrency(
        val currency: Currency
) : IDbCommand {

    override fun execute(db: AppDatabaseHelper): IResultDbCommand {

        val values = ContentValues()
        values.put(UserCurrencyTable.COLUMN_CODE_CURRENCY, currency.currencyCode)
        val rowId: Long = db.writableDatabase.insert(UserCurrencyTable.TABLE_NAME, null, values)

        return HolderResult.Success(rowId)
    }
}