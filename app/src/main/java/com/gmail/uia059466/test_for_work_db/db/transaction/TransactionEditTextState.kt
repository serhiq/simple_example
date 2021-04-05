package com.gmail.uia059466.test_for_work_db.db.transaction

import java.math.BigDecimal

data class TransactionEditTextState(
        val isUseCurrency: Boolean,
        val amountRub: String? = null,
        val amountKop: String? = null,
        val amountCurrency: String? = null,
        val amountOst: String? = null,
        val comment: String? = null
) {
    fun isCorrectState(): Boolean {
        return if (isUseCurrency) {
            isCorrectAmountCurrency()
        } else {
            isCorrectAmountRub()
        }
    }

    private fun isCorrectAmountRub(): Boolean {
        val summInKop = requestAmoutInKop()
        return summInKop != BigDecimal.ZERO
    }

    private fun isCorrectAmountCurrency(): Boolean {
        if (!isCorrectAmountRub()) return false
        return requestCurrencyAmount() != BigDecimal.ZERO
    }

    fun requestAmoutInKop(): BigDecimal {
        return BigDecimal(amountRub).multiply(BigDecimal(100)) + BigDecimal(amountKop)
    }

    fun requestCurrencyAmount(): BigDecimal {
        return BigDecimal(amountCurrency).multiply(BigDecimal(100)) + BigDecimal(amountOst)
    }
}