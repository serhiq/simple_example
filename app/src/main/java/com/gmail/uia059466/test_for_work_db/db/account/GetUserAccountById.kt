package com.gmail.uia059466.test_for_work_db.db.account

import android.database.Cursor
import com.gmail.uia059466.test_for_work_db.account.accountedit.IconAccount
import com.gmail.uia059466.test_for_work_db.account.accountedit.UserAccount
import com.gmail.uia059466.test_for_work_db.db.AppDatabaseHelper
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import java.math.BigDecimal

class GetUserAccountById(val rowId: Long) {

    fun execute(db: AppDatabaseHelper): HolderResult<UserAccount> {

        var account: UserAccount? = null

        val selectQuery = "SELECT * FROM " + AccountTable.TABLE_NAME + " WHERE rowid= " + rowId.toString() + " LIMIT 1"

        val cursor: Cursor = db.readableDatabase.rawQuery(selectQuery, null)
        try {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(AccountTable.COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndex(AccountTable.COLUMN_TITLE))

                val codeIcon = cursor.getString(cursor.getColumnIndex(AccountTable.COLUMN_CODE_ICON))
                val icon = IconAccount.fromString(codeIcon)


                val amountInt = cursor.getInt(cursor.getColumnIndex(AccountTable.COLUMN_AMOUNT))
                val amount = BigDecimal(amountInt
                )
                account = UserAccount(id = id, title = title, iconAccount = icon, amount = amount)
            }
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return if (account != null) {
            HolderResult.Success(account)
        } else {
            HolderResult.Error(Exception("Account Not Found"))
        }
    }
}