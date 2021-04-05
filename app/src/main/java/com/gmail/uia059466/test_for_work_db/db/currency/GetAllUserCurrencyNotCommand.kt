package com.gmail.uia059466.test_for_work_db.db.currency

import android.database.Cursor
import com.gmail.uia059466.test_for_work_db.db.AppDatabaseHelper
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import java.util.*

class GetAllUserCurrencyNotCommand{

    fun execute(db: AppDatabaseHelper): HolderResult<List<UserCurrency>> {

        val currincies: MutableList<UserCurrency> = mutableListOf<UserCurrency>()

        val selectQuery = "SELECT * FROM " + UserCurrencyTable.TABLE_NAME

        val cursor: Cursor = db.readableDatabase.rawQuery(selectQuery, null)
        try {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(UserCurrencyTable.COLUMN_ID))
                val codeCurrency = cursor.getString(cursor.getColumnIndex(UserCurrencyTable.COLUMN_CODE_CURRENCY))

                val currency = Currency.getInstance(codeCurrency)

                currincies.add(UserCurrency(id, codeCurrency, currency.displayName))
            }
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return HolderResult.Success(currincies)
    }
}