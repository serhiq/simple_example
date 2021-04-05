package com.gmail.uia059466.test_for_work_db.db.account

import android.content.ContentValues
import com.gmail.uia059466.test_for_work_db.account.accountedit.UserAccount
import com.gmail.uia059466.test_for_work_db.db.AppDatabaseHelper
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.db.IDbCommand

class UpdateUserAccount(
    val account: UserAccount
) : IDbCommand {
    override fun execute(db: AppDatabaseHelper): HolderResult<Int> {
        val values = ContentValues()
        values.put(AccountTable.COLUMN_TITLE, account.title)
        values.put(AccountTable.COLUMN_CODE_ICON, account.iconAccount.code)
        values.put(AccountTable.COLUMN_AMOUNT, account.amount.toPlainString())

        val selection: String = AccountTable.COLUMN_ID + " = ?"
        val selectionArgs = arrayOf<String>(java.lang.String.valueOf(account.id))

        val count: Int =
            db.writableDatabase.update(AccountTable.TABLE_NAME, values, selection, selectionArgs)

        return HolderResult.Success(count)
    }
}