package com.gmail.uia059466.test_for_work_db.account

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

object AmountFormatter {
    fun createAmountString(amount: BigDecimal): String {
        val displayVal: BigDecimal = amount.setScale(2, RoundingMode.HALF_EVEN)
        val dfs = DecimalFormatSymbols()
        dfs.decimalSeparator = ','
        dfs.groupingSeparator = ' '
        dfs.monetaryDecimalSeparator = ','
        dfs.currencySymbol = ""
        val df = DecimalFormat("#,##0.00", dfs)
        df.isGroupingUsed = dfs.groupingSeparator.toInt() > 0
        df.minimumFractionDigits = 0
        df.maximumFractionDigits = 2
        df.isDecimalSeparatorAlwaysShown = false
        return df.format(displayVal)
    }
}