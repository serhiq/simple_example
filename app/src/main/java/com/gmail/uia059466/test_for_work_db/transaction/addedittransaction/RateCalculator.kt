package com.gmail.uia059466.test_for_work_db.transaction.addedittransaction

import kotlin.math.abs

object RateCalculator {

    fun calculateInRub(rateStr: String,
                       amountCurrencyStr: NumberStr): NumberStr {
        val r = requestRate(rateStr)
        val amountFrom = requestAmount(amountCurrencyStr)
        val resultAmount = Math.round(r * amountFrom)

        val absAmount = abs(resultAmount)
        val numPart1 = absAmount / 100
        val y = absAmount - 100 * numPart1
        val numPart2 = String.format("%02d", y)
        return NumberStr(numPart1.toString(), numPart2.toString())

    }

    private fun requestRate(rateStr: String): Float {
        return try {
            var rateText: String = rateStr.trim()
            if (rateText != null) {
                rateText = rateText.replace(',', '.')
                return rateText.toFloat()
            }
            0F
        } catch (ex: java.lang.NumberFormatException) {
            0F
        }
    }

    private fun requestAmount(n: NumberStr): Long {
        val x = 100 * toLong(n.integerNumberPart)
        val y = toLong(n.fractionNumberPart)
        val amount = x + if (n.fractionNumberPart.length == 1) 10 * y else y
        return amount
    }

    private fun toLong(s: String?): Long {
        return try {
            if (s == null || s.isEmpty()) 0 else s.toLong()
        } catch (e: NumberFormatException) {
            0
        }
    }

    fun calculateInCurrencyAmount(rateStr: String,
                                  amountRubStr: NumberStr): NumberStr {
        val r = requestRate(rateStr)
        val amountRub = requestAmount(amountRubStr)
        val resultAmount = Math.round(amountRub / r)

        val absAmount = Math.abs(resultAmount)
        val numPart1 = absAmount / 100
        val y = absAmount - 100 * numPart1
        val numPart2 = String.format("%02d", y)
        return NumberStr(numPart1.toString(), numPart2.toString())
    }
}

class NumberStr(val integerNumberPart: String,
                val fractionNumberPart: String)
