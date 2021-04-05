package com.gmail.uia059466.test_for_work_db.db.account

import android.content.ContentValues
import com.gmail.uia059466.test_for_work_db.account.accountedit.UserAccount
import com.gmail.uia059466.test_for_work_db.db.AppDatabaseHelper
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.db.IDbCommand

class AddUserAccount(
        val account: UserAccount
) : IDbCommand {

    override fun execute(db: AppDatabaseHelper): HolderResult<Long> {
        val values = ContentValues()
        values.put(AccountTable.COLUMN_TITLE, account.title)
        values.put(AccountTable.COLUMN_CODE_ICON, account.iconAccount.code)
        values.put(AccountTable.COLUMN_AMOUNT, account.amount.toPlainString())
        val rowId: Long = db.writableDatabase.insert(AccountTable.TABLE_NAME, null, values)
        return HolderResult.Success(rowId)
    }
}