package com.gmail.uia059466.test_for_work_db.feature

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.gmail.uia059466.test_for_work_db.MainActivity
import com.gmail.uia059466.test_for_work_db.ServiceLocator
import com.gmail.uia059466.test_for_work_db.account.accountedit.IconAccount
import com.gmail.uia059466.test_for_work_db.account.accountedit.UserAccount
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource
import com.gmail.uia059466.test_for_work_db.db.account.AddUserAccount
import com.gmail.uia059466.test_for_work_db.db.rates.AddUserRate
import com.gmail.uia059466.test_for_work_db.db.rates.RatesUser
import com.gmail.uia059466.test_for_work_db.screen.AccountListScreen
import com.gmail.uia059466.test_for_work_db.utils.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class TransactionTestRateRequest : BaseTest() {

    private lateinit var db: LocalDataSource

    //    при выборе валюты, подтягивается курс из справочника точно по выбранной дате
    @Test
    fun createOneIncomeOperationInteger() {
        runBlocking {
            insertTestAccountAndTestRate()

            val activityScenario = ActivityScenario.launch(MainActivity::class.java)

//      Given
            val accountsScreen = AccountListScreen(device)
            val accountDetailScreen = accountsScreen.clickOnList(0)
            accountDetailScreen.checkAmount("0")
            accountDetailScreen.checkTitle("Тестовый")
            val operation = accountDetailScreen.clickOnIncome()

            val dialogDate = operation.tapOnDate()
            dialogDate.selectDate(3)


            val dialog = operation.tapOnCurrency()
            dialog.selectCurrency("EUR")


            operation.waitForScreenToBeDisplayed()

            operation.inputAmountInCurrency("100,00")
            operation.checkSumInRub("20000,00")
            sleep(300)
            operation.pressOnSave()
            sleep(300)
            accountDetailScreen.checkAmount("20 000")
            sleep(500)
            activityScenario.close()
        }
    }

    //    при выборе валюты, если нет курса в справочнике точно по выбраной дате, то значение 1
    @Test
    fun ifnoRateThenRateEqualZeroByDefault() {
        runBlocking {
            insertTestAccountAndTestRate()

            val activityScenario = ActivityScenario.launch(MainActivity::class.java)

//      Given
            val accountsScreen = AccountListScreen(device)
            val accountDetailScreen = accountsScreen.clickOnList(0)
            accountDetailScreen.checkAmount("0")
            accountDetailScreen.checkTitle("Тестовый")
            val operation = accountDetailScreen.clickOnIncome()

            val dialogDate = operation.tapOnDate()
            dialogDate.selectDate(2)


            val dialog = operation.tapOnCurrency()
            dialog.selectCurrency("EUR")


            sleep(2000)
            operation.waitForScreenToBeDisplayed()

            operation.checkRate("1")
            activityScenario.close()
        }
    }


    private suspend fun insertTestAccountAndTestRate() {
        db.execute(
            AddUserAccount(
                UserAccount(
                    id = 0,
                    title = "Тестовый",
                    iconAccount = IconAccount.PIG,
                    amount = BigDecimal.ZERO
                )
            )
        )

//
        val currencyEUR = Currency.getInstance("EUR")

        val dateAprilFirst = GregorianCalendar(2021, Calendar.APRIL, 3).time
        val rateAprilFirst = "200".toBigDecimal()
        db.execute(
            AddUserRate(
                rate = RatesUser(
                    id = 0,
                    currency = currencyEUR,
                    rate = rateAprilFirst,
                    data = dateAprilFirst
                )
            )
        )


        val dateAprilTen = GregorianCalendar(2021, Calendar.APRIL, 10).time
        val rateAprilTen = "500".toBigDecimal()
        db.execute(
            AddUserRate(
                rate = RatesUser(
                    id = 0,
                    currency = currencyEUR,
                    rate = rateAprilTen,
                    data = dateAprilTen
                )
            )
        )


        val dateApril = GregorianCalendar(2021, Calendar.APRIL, 3).time
        val rateApril = "50.56".toBigDecimal()
        db.execute(
            AddUserRate(
                rate = RatesUser(
                    id = 0,
                    currency = currencyEUR,
                    rate = rateApril,
                    data = dateApril
                )
            )
        )

        val dateMarch = GregorianCalendar(2021, Calendar.APRIL, 1).time
        val rateMarch = "100".toBigDecimal()
        db.execute(
            AddUserRate(
                rate = RatesUser(
                    id = 0,
                    currency = currencyEUR,
                    rate = rateMarch,
                    data = dateMarch
                )
            )
        )


    }


    @Before
    fun init() {
        db =
            ServiceLocator.provideListDataSource(
                ApplicationProvider.getApplicationContext()
            )
    }

    @After
    fun reset() {
        ServiceLocator.resetDatabase()
    }
}