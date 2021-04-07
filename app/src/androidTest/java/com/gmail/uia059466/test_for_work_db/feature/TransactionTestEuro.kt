package com.gmail.uia059466.test_for_work_db.feature

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.gmail.uia059466.test_for_work_db.MainActivity
import com.gmail.uia059466.test_for_work_db.ServiceLocator
import com.gmail.uia059466.test_for_work_db.account.accountedit.IconAccount
import com.gmail.uia059466.test_for_work_db.account.accountedit.UserAccount
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource
import com.gmail.uia059466.test_for_work_db.db.account.AddUserAccount
import com.gmail.uia059466.test_for_work_db.screen.AccountListScreen
import com.gmail.uia059466.test_for_work_db.utils.BaseTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class TransactionTestEuro : BaseTest() {

    private lateinit var db: LocalDataSource

    @Test
    fun createOneIncomeOperationInteger() {
        runBlocking {
            insertTestAccount()

            val activityScenario = ActivityScenario.launch(MainActivity::class.java)

//      Given
            val accountsScreen = AccountListScreen(device)
            val accountDetailScreen = accountsScreen.clickOnList(0)
            accountDetailScreen.checkAmount("0")
            accountDetailScreen.checkTitle("Тестовый")

            val operation = accountDetailScreen.clickOnIncome()
            val dialog= operation.tapOnCurrency()
            dialog.selectCurrency("EUR")
            operation.waitForScreenToBeDisplayed()

            operation.inputAmountInCurrency("100,00")
            operation.checkSumInRub("100,00")
            sleep(300)
            operation.pressOnSave()
            sleep(300)
            accountDetailScreen.checkAmount("100")
            sleep(500)
            activityScenario.close()

        }
    }

    @Test
    fun createOneIncomeOperationIntegerEnterRate() {
        runBlocking {
            insertTestAccount()

            val activityScenario = ActivityScenario.launch(MainActivity::class.java)

//      Given
            val accountsScreen = AccountListScreen(device)
            val accountDetailScreen = accountsScreen.clickOnList(0)
            accountDetailScreen.checkAmount("0")
            accountDetailScreen.checkTitle("Тестовый")

            val operation = accountDetailScreen.clickOnIncome()
            val dialog= operation.tapOnCurrency()
            dialog.selectCurrency("EUR")
            operation.waitForScreenToBeDisplayed()

            operation.inputAmountInCurrency("100,00")
            operation.inputRate("50,600121")
            operation.checkSumInRub("5060,01")
            operation.pressOnSave()

            accountDetailScreen.waitForScreenToBeDisplayed()
            accountDetailScreen.checkAmount("5 060,01")
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