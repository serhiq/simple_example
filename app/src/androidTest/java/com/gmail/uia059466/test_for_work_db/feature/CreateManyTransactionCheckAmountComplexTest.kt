package com.gmail.uia059466.test_for_work_db.feature

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.gmail.uia059466.test_for_work_db.MainActivity
import com.gmail.uia059466.test_for_work_db.ServiceLocator
import com.gmail.uia059466.test_for_work_db.account.accountedit.IconAccount
import com.gmail.uia059466.test_for_work_db.account.accountedit.UserAccount
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource
import com.gmail.uia059466.test_for_work_db.db.account.AddUserAccount
import com.gmail.uia059466.test_for_work_db.db.currency.AddUserCurrency
import com.gmail.uia059466.test_for_work_db.screen.AccountListScreen
import com.gmail.uia059466.test_for_work_db.utils.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class CreateManyTransactionCheckAmountComplexTest : BaseTest() {

    private lateinit var db: LocalDataSource
    /*

    итоговый
    + 100 rub
    - 20 rub

    + 200 EUR (rate 85,85)
    - 20 USD (25,11)

    - 50 TRY (0,5)
    + 3 TRY
     */

    @Test
    fun feature2() {
        runBlocking {
            insertTestAccount()

            val activityScenario = ActivityScenario.launch(MainActivity::class.java)

//      Given
            val accountsScreen = AccountListScreen(device)
            val accountDetailScreen = accountsScreen.clickOnList(0)
            accountDetailScreen.checkAmount("0")
            accountDetailScreen.checkTitle("Тестовый")

//             + 100 руб
            val operation = accountDetailScreen.clickOnIncome()
            operation.inputAmountInRub("100,00")
            operation.pressOnSave()

//       проверка суммы на экране счета
            accountDetailScreen.checkAmount("100")

//            -20 руб
            accountDetailScreen.clickOnOutcome()
            operation.inputAmountInRub("20,00")
            operation.pressOnSave()

            accountDetailScreen.checkAmount("80")

//            +200 eur rate 85,85
            accountDetailScreen.clickOnIncome()
            val selectCurrencyDialog=operation.tapOnCurrency()
            selectCurrencyDialog.selectCurrency("EUR")
            operation.inputRate("85,85")
            operation.inputAmountInCurrency("200,00")
            operation.pressOnSave()

            accountDetailScreen.checkAmount("17 250")

            //            - 20 USD rate 25,11
            accountDetailScreen.clickOnOutcome()
            operation.tapOnCurrency()

            selectCurrencyDialog.selectCurrency("USD")
            operation.inputRate("25,11")
            operation.inputAmountInCurrency("20,00")
            operation.pressOnSave()

            accountDetailScreen.checkAmount("16 747,8")


            //               - 50 TRY (0,5)
            accountDetailScreen.clickOnOutcome()
            operation.tapOnCurrency()

            selectCurrencyDialog.selectCurrency("TRY")
            operation.inputRate("0,5")
            operation.inputAmountInCurrency("50,00")
            operation.pressOnSave()

            accountDetailScreen.checkAmount("16 722,8")

            //               + 3 TRY (0,5)
            accountDetailScreen.clickOnIncome()
            operation.tapOnCurrency()

            selectCurrencyDialog.selectCurrency("TRY")
            operation.inputRate("0,5")
            operation.inputAmountInCurrency("3,00")
            operation.pressOnSave()

            accountDetailScreen.checkAmount("16 724,3")


            activityScenario.close()
        }
    }

    private suspend fun insertTestAccount() {
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

        db.execute(
            AddUserCurrency(currency = Currency.getInstance("USD"))
            )

        db.execute(
            AddUserCurrency(currency = Currency.getInstance("TRY"))
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