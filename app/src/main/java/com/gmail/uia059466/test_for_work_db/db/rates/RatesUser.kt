package com.gmail.uia059466.test_for_work_db.db.rates

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

data class RatesUser(
        val id: Long,
        val currency: Currency,
        val rate: BigDecimal,
        val data: Date
) {
    companion object {
        private val nf = DecimalFormat("0.00000")
        private val rateShort = DecimalFormat("0.00")

        fun createRateDescription(currentRate: BigDecimal,
                                  currencyFrom: Currency): String {
            val r: Double = currentRate.toDouble()
            val sb = StringBuilder()
            val currencyTo: Currency = Currency.getInstance("RUB")
            sb.append("1")
                    .append(currencyFrom.currencyCode)
                    .append("=")
                    .append(nf.format(r))
                    .append(currencyTo.currencyCode)
                    .append(", ")
            sb.append("1")
                    .append(currencyTo.currencyCode)
                    .append("=")
                    .append(nf.format(1.0 / r))
                    .append(currencyFrom.currencyCode)

            return sb.toString()
        }
    }

    fun createDescriptionForList(): String {
        val simpleFormat = SimpleDateFormat("dd MMMM")
        val data = simpleFormat.format(data)


        val r: Double = rate.toDouble()
        val sb = StringBuilder()
        val currencyTo: Currency = Currency.getInstance("RUB")
        sb.append(data)
        sb.append("    ")
        sb.append("1")
                .append(currency.currencyCode)
                .append(" = ")
                .append(rateShort.format(r))
                .append("  ")
                .append(currencyTo.currencyCode)
        return sb.toString()

    }

}
