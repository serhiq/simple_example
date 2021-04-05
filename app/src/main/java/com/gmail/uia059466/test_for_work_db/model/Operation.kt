package com.gmail.uia059466.test_for_work_db.model

import java.math.BigDecimal
import java.util.*

class Operation(
        val typeOperation: TypeOperation,
        val account: Account,
        val comment: String,
        val amount: Int,
        val realAmount: Int? = null,
        val realCurrency: Currency? = null,
        val convertComment: String
) {

    fun requestResultOperation(): BigDecimal {
        return when (typeOperation) {
            TypeOperation.INCOME -> calculateSum(amount, account.amount)
            TypeOperation.OUTCOME -> calculateSub(amount, account.amount)
        }
    }

    private fun calculateSub(a1: Int,
                             a2: Int): BigDecimal {
        return BigDecimal(a1).subtract(BigDecimal(a2))
    }

    private fun calculateSum(a1: Int,
                             a2: Int): BigDecimal {
        return BigDecimal(a1).plus(BigDecimal(a2))
    }
}