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

class TransactionTestInRub : BaseTest() {

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
            operation.inputAmountInRub("10,00")
            operation.pressOnSave()

//       проверка суммы на экране счета
            accountDetailScreen.checkAmount("10")


//      проверка суммы на экране списка счетов
            device.pressBack()
            sleep(500)

            accountsScreen.checkItemView(0, "Тестовый", "10")
            activityScenario.close()
        }
    }

    @Test
    fun createOneIncomeOperationFloat() {
        runBlocking {
            insertTestAccount()
            val activityScenario = ActivityScenario.launch(MainActivity::class.java)

//      Given
            val accountsScreen = AccountListScreen(device)
            val accountDetailScreen = accountsScreen.clickOnList(0)
            accountDetailScreen.checkAmount("0")
            accountDetailScreen.checkTitle("Тестовый")

            val operation = accountDetailScreen.clickOnIncome()
            operation.inputAmountInRub("10,50")
            operation.pressOnSave()
            sleep(30)
            accountDetailScreen.checkAmount("10,5")

            //      проверка суммы на экране списка счетов
            device.pressBack()
            sleep(300)

            accountsScreen.checkItemView(0, "Тестовый", "10,5")
            activityScenario.close()
        }
    }

    @Test
    fun createOneOutcomeOperationInteger() {
        runBlocking {
            insertTestAccount()

            val activityScenario = ActivityScenario.launch(MainActivity::class.java)

//      Given
            val accountsScreen = AccountListScreen(device)
            val accountDetailScreen = accountsScreen.clickOnList(0)
            accountDetailScreen.checkAmount("0")
            accountDetailScreen.checkTitle("Тестовый")

            val operation = accountDetailScreen.clickOnOutcome()
            operation.inputAmountInRub("10,00")
            operation.pressOnSave()

//       проверка суммы на экране счета
            accountDetailScreen.checkAmount("-10")
            sleep(300)

//      проверка суммы на экране списка счетов
            device.pressBack()
            sleep(300)

            accountsScreen.checkItemView(0, "Тестовый", "-10")
            activityScenario.close()
        }
    }

    @Test
    fun createOneOutcomeOperationFloat() {
        runBlocking {
            insertTestAccount()
            val activityScenario = ActivityScenario.launch(MainActivity::class.java)

//      Given
            val accountsScreen = AccountListScreen(device)
            val accountDetailScreen = accountsScreen.clickOnList(0)
            accountDetailScreen.checkAmount("0")
            accountDetailScreen.checkTitle("Тестовый")

            val operation = accountDetailScreen.clickOnOutcome()
            operation.inputAmountInRub("10,50")
            operation.pressOnSave()
            sleep(300)
            accountDetailScreen.checkAmount("-10,5")

            //      проверка суммы на экране списка счетов
            device.pressBack()
            sleep(300)

            accountsScreen.checkItemView(0, "Тестовый", "-10,5")
            activityScenario.close()
        }
    }

    @Test
    fun createTwoIncomeTwoOutcomeOperation() {
        runBlocking {
            insertTestAccount()
            val activityScenario = ActivityScenario.launch(MainActivity::class.java)

//      Given
            val accountsScreen = AccountListScreen(device)
            val accountDetailScreen = accountsScreen.clickOnList(0)
            accountDetailScreen.checkAmount("0")
            accountDetailScreen.checkTitle("Тестовый")

            val operation = accountDetailScreen.clickOnOutcome()
            operation.inputAmountInRub("10,50")
            operation.pressOnSave()
            sleep(300)
            accountDetailScreen.checkAmount("-10,5")

            accountDetailScreen.clickOnOutcome()
            operation.inputAmountInRub("10,50")
            operation.pressOnSave()
            sleep(300)
            accountDetailScreen.checkAmount("-21")

            accountDetailScreen.clickOnIncome()
            operation.inputAmountInRub("100,1")
            operation.pressOnSave()
            sleep(300)
            accountDetailScreen.checkAmount("79,1")

            accountDetailScreen.clickOnIncome()
            operation.inputAmountInRub("20,75")
            operation.pressOnSave()
            sleep(300)
            accountDetailScreen.checkAmount("99,85")



            //      проверка суммы на экране списка счетов
            device.pressBack()
            sleep(300)

            accountsScreen.checkItemView(0, "Тестовый", "99,85")
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