package com.gmail.uia059466.test_for_work_db.transaction.addedittransaction

import com.gmail.uia059466.test_for_work_db.account.accountedit.UserAccount
import com.gmail.uia059466.test_for_work_db.db.transaction.TransactionDataBase
import com.gmail.uia059466.test_for_work_db.db.transaction.TypeOperation
import java.math.BigDecimal
import java.util.*

data class TransactionSingleDisplay(
        val id: Long,
        val codeTransaction: String,
        val account: UserAccount,
        val date: Date,
        val note: String,
        val amount: BigDecimal = BigDecimal.ZERO,
        val fromAmount: BigDecimal = BigDecimal.ZERO,
        val fromCurrency: String = Currency.getInstance("RUB").currencyCode) {


    private fun asDatabase(): TransactionDataBase {


        return TransactionDataBase(id = id,
                                   codeTransaction = codeTransaction,
                                   fromAccountId = account.id,
                                   date = date.time, note = note,
                                   amount = amount,
                                   fromAmount = fromAmount,
                                   fromCurrency = fromCurrency)
    }

    fun asTransactionDataWithNewAccount(): TransactionDataWithNewAccount {


        val result = when (codeTransaction) {
            TypeOperation.INCOME.code  -> account.amount.plus(amount)
            TypeOperation.OUTCOME.code -> account.amount.minus(amount)
            else                       -> BigDecimal.ZERO
        }
        val newAccount = account.copy(amount = result)
        return TransactionDataWithNewAccount(transactionDb = this.asDatabase(), newAccount = newAccount)
    }
}

class TransactionDataWithNewAccount(
        val transactionDb: TransactionDataBase,
        val newAccount: UserAccount)
