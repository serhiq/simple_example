package com.gmail.uia059466.test_for_work_db.db.rates

import android.content.ContentValues
import com.gmail.uia059466.test_for_work_db.db.*

class AddUserRate(
        val rate: RatesUser
) : IDbCommand {

    override fun execute(db: AppDatabaseHelper): IResultDbCommand {
        val values = ContentValues()
        values.put(UserRateTable.COLUMN_CODE_CURRENCY, rate.currency.currencyCode)
        values.put(UserRateTable.COLUMN_CODE_RATE, rate.rate.toPlainString())
        values.put(UserRateTable.COLUMN_CODE_DATE, rate.data.time)
        val rowId: Long = db.writableDatabase.insert(UserRateTable.TABLE_NAME, null, values)
        return HolderResult.Success(rowId)
    }
}