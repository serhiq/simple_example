package com.gmail.uia059466.test_for_work_db.db.rates

import android.database.Cursor
import com.gmail.uia059466.test_for_work_db.db.AppDatabaseHelper
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.db.UserRateTable
import java.math.BigDecimal
import java.util.*

class GetRateById(
        val rowId: Long

) {
    fun execute(db: AppDatabaseHelper): HolderResult<RatesUser> {

        var rateUser: RatesUser? = null

        val selectQuery = "SELECT * FROM " + UserRateTable.TABLE_NAME + " WHERE rowid= " + rowId.toString() + " LIMIT 1"

        val cursor: Cursor = db.readableDatabase.rawQuery(selectQuery, null)
        try {
            while (cursor.moveToNext()) {

                val id = cursor.getLong(cursor.getColumnIndex(UserRateTable.COLUMN_ID))
                val codeCurrency = cursor.getString(cursor.getColumnIndex(UserRateTable.COLUMN_CODE_CURRENCY))
                val currency = Currency.getInstance(codeCurrency)
                val rateStr = cursor.getString(cursor.getColumnIndex(UserRateTable.COLUMN_CODE_RATE))
                val rate = BigDecimal(rateStr)
                val dateLong = cursor.getLong(cursor.getColumnIndex(UserRateTable.COLUMN_CODE_DATE))
                val date = Date(dateLong)

                rateUser = RatesUser(id = id, currency = currency, rate = rate, data = date)
            }
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return if (rateUser != null) {
            HolderResult.Success(rateUser)
        } else {
            HolderResult.Error(Exception("Currincies NotFound"))
        }
    }
}