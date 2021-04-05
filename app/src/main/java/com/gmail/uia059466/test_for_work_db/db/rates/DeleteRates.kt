package com.gmail.uia059466.test_for_work_db.db.rates

import com.gmail.uia059466.test_for_work_db.db.AppDatabaseHelper
import com.gmail.uia059466.test_for_work_db.db.HolderResult

class DeleteRates(private val idRates: Long

) {

    fun execute(db: AppDatabaseHelper): HolderResult.Success<Int> {

        val selection: String = UserRateTable.COLUMN_ID.toString() + " = ?"
        val selectionArgs = arrayOf<String>(idRates.toString())

        val count: Int = db.writableDatabase.delete(UserRateTable.TABLE_NAME, selection, selectionArgs)

        return HolderResult.Success(count)
    }
}