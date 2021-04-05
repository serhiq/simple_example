package com.gmail.uia059466.test_for_work_db.db.currency

import android.database.Cursor
import com.gmail.uia059466.test_for_work_db.db.AppDatabaseHelper
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import java.util.*

class GetAUserCurrencyById(
        val rowId: Long

) {

    fun execute(db: AppDatabaseHelper): HolderResult<UserCurrency> {

        var displayCurrency: UserCurrency? = null

        val selectQuery = "SELECT * FROM " + UserCurrencyTable.TABLE_NAME + " WHERE rowid= " + rowId.toString() + " LIMIT 1"

        val cursor: Cursor = db.readableDatabase.rawQuery(selectQuery, null)
        try {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(UserCurrencyTable.COLUMN_ID))
                val codeCurrency = cursor.getString(cursor.getColumnIndex(UserCurrencyTable.COLUMN_CODE_CURRENCY))

                val currency = Currency.getInstance(codeCurrency)
                displayCurrency = UserCurrency(id, codeCurrency, currency.displayName)


            }
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return if (displayCurrency != null) {
            HolderResult.Success(displayCurrency)
        } else {
            HolderResult.Error(Exception("Currincies NotFound"))
        }
    }
}