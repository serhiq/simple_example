package com.gmail.uia059466.test_for_work_db.db.currency

import com.gmail.uia059466.test_for_work_db.db.AppDatabaseHelper
import com.gmail.uia059466.test_for_work_db.db.HolderResult

class DeleteCurrency(private val idUserCurrency: Long) {

    fun execute(db: AppDatabaseHelper): HolderResult.Success<Int> {

        val selection: String = UserCurrencyTable.COLUMN_ID.toString() + " = ?"
        val selectionArgs = arrayOf<String>(idUserCurrency.toString())

        val count: Int = db.writableDatabase.delete(UserCurrencyTable.TABLE_NAME, selection, selectionArgs)

        return HolderResult.Success(count)
    }
}