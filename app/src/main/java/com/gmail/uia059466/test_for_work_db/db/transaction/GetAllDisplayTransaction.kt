package com.gmail.uia059466.test_for_work_db.db.transaction

import android.database.Cursor
import com.gmail.uia059466.test_for_work_db.db.AppDatabaseHelper
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import java.math.BigDecimal

class GetAllTransactionDatabase {
    fun execute(db: AppDatabaseHelper): HolderResult<List<TransactionDataBase>> {

        val rates: MutableList<TransactionDataBase> = mutableListOf<TransactionDataBase>()

        val selectQuery = "SELECT * FROM ${TransactionTable.TABLE_NAME}  order by ${TransactionTable.COLUMN_CODE_DATE} DESC"


        val cursor: Cursor = db.readableDatabase.rawQuery(selectQuery, null)
        try {
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(TransactionTable.COLUMN_ID))
                val codeTransaction = cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_CODE_TRANSACTION))
                val fromAccountId = cursor.getLong(cursor.getColumnIndex(TransactionTable.COLUMN_FROM_ACCOUNT_ID))

                val dateLong = cursor.getLong(cursor.getColumnIndex(TransactionTable.COLUMN_CODE_DATE))

                val note = cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_CODE_NOTE))

                val amountStr = cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_CODE_AMOUNT))
                val amount = BigDecimal(amountStr)

                val fromAmountStr = cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_CODE_FROM_AMOUNT))
                val fromAmount = BigDecimal(fromAmountStr)

                val fromCurrencyCode = cursor.getString(cursor.getColumnIndex(TransactionTable.COLUMN_CODE_FROM_CURRENCY))

                val transaction = TransactionDataBase(
                        id = id,
                        codeTransaction = codeTransaction,
                        fromAccountId = fromAccountId,
                        date = dateLong,
                        note = note,
                        amount = amount,
                        fromAmount = fromAmount,
                        fromCurrency = fromCurrencyCode)


                rates.add(transaction)
            }
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return HolderResult.Success(rates)
    }
}