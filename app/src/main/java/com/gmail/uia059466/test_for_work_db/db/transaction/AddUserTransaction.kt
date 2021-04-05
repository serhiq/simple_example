package com.gmail.uia059466.test_for_work_db.db.transaction

import android.content.ContentValues
import com.gmail.uia059466.test_for_work_db.db.*
import com.gmail.uia059466.test_for_work_db.transaction.TransactionDataBase

class AddUserTransaction(
        val t: TransactionDataBase
): IDbCommand {

    override fun execute(db: AppDatabaseHelper): HolderResult<Long> {
            val values = ContentValues()
            values.put(TransactionTable.COLUMN_CODE_TRANSACTION, t.codeTransaction)
            values.put(TransactionTable.COLUMN_FROM_ACCOUNT_ID, t.fromAccountId)
            values.put(TransactionTable.COLUMN_CODE_DATE, t.date)
            values.put(TransactionTable.COLUMN_CODE_NOTE, t.note)
            values.put(TransactionTable.COLUMN_CODE_AMOUNT, t.amount.toPlainString())
            values.put(TransactionTable.COLUMN_CODE_FROM_AMOUNT, t.fromAmount.toPlainString())
            values.put(TransactionTable.COLUMN_CODE_FROM_CURRENCY, t.fromCurrency)

            val rowId: Long = db.writableDatabase.insert(TransactionTable.TABLE_NAME, null, values)



        return HolderResult.Success(rowId)
    }
}