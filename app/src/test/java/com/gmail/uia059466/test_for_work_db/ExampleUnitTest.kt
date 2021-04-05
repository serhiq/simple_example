package com.gmail.uia059466.test_for_work_db

import com.gmail.uia059466.test_for_work_db.transaction.addedittransaction.NumberStr
import com.gmail.uia059466.test_for_work_db.transaction.addedittransaction.RateCalculator
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun calculateRateInRub() {
        val result = RateCalculator.calculateInRub("10", NumberStr("1", "0"))

        assertEquals(result.integerNumberPart, "10")
        assertEquals(result.fractionNumberPart, "00")
    }

    @Test
    fun calculateRateInRubOther() {
        val result = RateCalculator.calculateInRub("0", NumberStr("1", "0"))

        assertEquals(result.integerNumberPart, "0")
        assertEquals(result.fractionNumberPart, "00")
    }

    @Test
    fun calculateRateInRubOther2() {
        val result = RateCalculator.calculateInRub("73", NumberStr("10", "0"))

        assertEquals(result.integerNumberPart, "730")
        assertEquals(result.fractionNumberPart, "00")
    }
    @Test

    fun calculateRateInRubWithFractal() {
        val result = RateCalculator.calculateInRub("70,4", NumberStr("0", "5"))

        assertEquals(result.integerNumberPart, "35")
        assertEquals(result.fractionNumberPart, "20")
    }
    @Test

    fun calculateInRubAmouuntInCurrencyChaged() {
        val result = RateCalculator.calculateInCurrencyAmount("1", NumberStr("1", "0"))

        assertEquals(result.integerNumberPart, "1")
        assertEquals(result.fractionNumberPart, "00")
    }

    @Test
    fun calculateInRubAmouuntInCurrencyChaged2() {
        val result = RateCalculator.calculateInCurrencyAmount("10", NumberStr("100", "0"))

        assertEquals(result.integerNumberPart, "10")
        assertEquals(result.fractionNumberPart, "00")
    }

    @Test
    fun calculateInRubAmouuntInCurrencyChaged3() {
        val result = RateCalculator.calculateInCurrencyAmount("10,75", NumberStr("100", "0"))

        assertEquals(result.integerNumberPart, "9")
        assertEquals(result.fractionNumberPart, "30")
    }
}