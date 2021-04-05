package com.gmail.uia059466.test_for_work_db.db.rates

import android.database.Cursor
import com.gmail.uia059466.test_for_work_db.db.AppDatabaseHelper
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.utils.toBeginDay
import com.gmail.uia059466.test_for_work_db.utils.toEndDay
import java.util.*

class GetLastRateByCurrencyAndDate(
        private val codeCurrency: String,
        private val date: Date) {
    fun execute(db: AppDatabaseHelper): HolderResult<String> {

        val beginDay = toBeginDay(date).time
        val endDay = toEndDay(date).time

        val selectQuery = """SELECT * FROM ${UserRateTable.TABLE_NAME} WHERE ${UserRateTable.COLUMN_CODE_CURRENCY} = "${codeCurrency}" AND ${UserRateTable.COLUMN_CODE_DATE} BETWEEN ${beginDay} AND ${endDay}  order by  ${UserRateTable.COLUMN_CODE_DATE}  DESC  LIMIT 1"""

        var rateStr = ""
        val cursor: Cursor = db.readableDatabase.rawQuery(selectQuery, null)
        try {
            while (cursor.moveToNext()) {

                rateStr = cursor.getString(cursor.getColumnIndex(UserRateTable.COLUMN_CODE_RATE))
                return HolderResult.Success(rateStr)

            }
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }

        return HolderResult.Error(Exception("Rate Not Found"))

    }
}