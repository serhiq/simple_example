package com.gmail.uia059466.test_for_work_db.db.transaction

import com.gmail.uia059466.test_for_work_db.account.AmountFormatter
import com.gmail.uia059466.test_for_work_db.transaction.DisplayTransaction
import com.gmail.uia059466.test_for_work_db.transaction.TransactionType
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class TransactionDataBase(
        val id: Long,
        val codeTransaction: String,
        val fromAccountId: Long,
        val date: Long,
        val note: String,
        val amount: BigDecimal,
        val fromAmount: BigDecimal,
        val fromCurrency: String
) {
    fun createCaptionHeader(): String {
        val date=Date(date)
        val simpleFormat = SimpleDateFormat("dd MMMM")
        return  simpleFormat.format(date)
    }
}
fun TransactionDataBase.asDisplayTransaction(): DisplayTransaction.TransactionDisplay {
    val amountStr=AmountFormatter.createAmountString(this.amount.divide(BigDecimal(100)))
    val sign=when(this.codeTransaction){
        TransactionType.OUTCOME.code ->"-"
        else                         -> ""
    }
    val typeOperation=when(this.codeTransaction){
        TransactionType.OUTCOME.code ->"Расход"
        TransactionType.INCOME.code  ->"Доход"
        else                         -> ""
    }
    val currencyDescription= if (fromCurrency=="RUB"){
        ""
    }else{
        val currencyAmountStr=AmountFormatter.createAmountString(this.fromAmount.divide(BigDecimal(100)))
        "$sign ${currencyAmountStr} ${this.fromCurrency}"

    }



    return DisplayTransaction.TransactionDisplay(title = this.note, amount = "$sign $amountStr RUB", id = this.id, amountCurrency = currencyDescription)
}
