package com.gmail.uia059466.test_for_work_db.db.account

import com.gmail.uia059466.test_for_work_db.db.AppDatabaseHelper
import com.gmail.uia059466.test_for_work_db.db.HolderResult

class DeleteAccount(val idAccount: Long) {
    fun execute(db: AppDatabaseHelper): HolderResult.Success<Int> {

        val selection: String = AccountTable.COLUMN_ID.toString() + " = ?"
        val selectionArgs = arrayOf<String>(idAccount.toString())

        val count: Int = db.writableDatabase.delete(AccountTable.TABLE_NAME, selection, selectionArgs)

        return HolderResult.Success(count)
    }
}