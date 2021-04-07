package com.gmail.uia059466.test_for_work_db.feature

import androidx.test.core.app.ApplicationProvider
import com.gmail.uia059466.test_for_work_db.utils.BaseTest
import com.gmail.uia059466.test_for_work_db.ServiceLocator
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource
import com.gmail.uia059466.test_for_work_db.screen.AccountListScreen
import com.gmail.uia059466.test_for_work_db.utils.RotoDevice
import org.junit.After
import org.junit.Before
import org.junit.Test

class CreateAccountTest : BaseTest() {

    private lateinit var db: LocalDataSource

    @Test
    fun createOneAccount() {
//      Given
        val d = RotoDevice(device)
        val listAccountScreen = AccountListScreen(device)
        listAccountScreen.checkSizeRecycler(0)
        val s = listAccountScreen.openAddAccount()
        d.checkOpen(RotoDevice.typeScreen.AccountList)

//      When
        s.inputTitle("Новый счет")
        s.pressOnSave()

//        Проверяем что название счета, и сумма появились в списке счетов
        d.checkOpen(RotoDevice.typeScreen.AccountList)
        listAccountScreen.checkItemView(0, title = "Новый счет", amount = "0")
    }

    @Test
    fun createTwoAccount() {
//      Given
        val d = RotoDevice(device)
        val listAccountScreen = AccountListScreen(device)
        listAccountScreen.checkSizeRecycler(0)
        val s = listAccountScreen.openAddAccount()
        d.checkOpen(RotoDevice.typeScreen.AccountList)

//      When
        s.inputTitle("Первый счет")
        s.pressOnSave()

//        Проверяем что название счета, и сумма появились в списке счетов
        d.checkOpen(RotoDevice.typeScreen.AccountList)
        listAccountScreen.checkItemView(0, title = "Первый счет", amount = "0")

//        Создаем второй счет
        listAccountScreen.checkSizeRecycler(1)
        d.checkOpen(RotoDevice.typeScreen.AccountList)
        listAccountScreen.openAddAccount()

        s.inputTitle("Второй счет")
        s.pressOnSave()
//        Проверяем что первый и второй счет действительно отображаются в списке счетов
        d.checkOpen(RotoDevice.typeScreen.AccountList)
        listAccountScreen.checkItemView(0, title = "Первый счет", amount = "0")
        listAccountScreen.checkItemView(1, title = "Второй счет", amount = "0")
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