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

class DeleteAccountTest : BaseTest() {

    private lateinit var db: LocalDataSource

    @Test
    fun deleteAccount() {
        runBlocking {
            insertTestAccount()
            val activityScenario = ActivityScenario.launch(MainActivity::class.java)

            val accountsScreen = AccountListScreen(device)
            accountsScreen.checkSizeRecycler(1)

            val accountDetailScreen = accountsScreen.clickOnList(0)
            accountDetailScreen.checkAmount("0")
            accountDetailScreen.checkTitle("Тестовый")
            val dialogDelete=accountDetailScreen.clickOnDelete()
            sleep(300)
            dialogDelete.clickDelete()

            // счет удален список счетов пустой
            accountsScreen.checkSizeRecycler(0)


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