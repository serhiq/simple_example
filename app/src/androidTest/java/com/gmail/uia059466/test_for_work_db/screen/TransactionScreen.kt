package com.gmail.uia059466.test_for_work_db.screen

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.uiautomator.UiDevice
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.screen.dialog.SelectCurrencyDialogScreen
import com.gmail.uia059466.test_for_work_db.utils.BaseScreen

class TransactionScreen(on: UiDevice) : BaseScreen(on) {

    override val contentLayout: Int = R.id.add_edit_transaction_content

    private val caption = R.id.text

    private val etAmountPrimaryInteger = R.id.primary_main_edit_text
    private val etAmountPrimaryFloat = R.id.main_secondary_currency

    private val etAmountSecondaryInteger = R.id.primary_secondary_edit_text
    private val etAmountSecondaryFloat = R.id.secondary_secondary_currency

    private val etRate = R.id.rate

    private val dateRv = R.id.date_rl
    private val dateTv = R.id.current_date_tv

    private val accountRv = R.id.account_rl
    private val accountTv = R.id.account_current_tv

    private val currencyRv = R.id.currency_rl
    private val currencyTv = R.id.current_currency_tv

    private val commentEt = R.id.comment_et


    override fun checkDefaultLayout() {

    }

    fun checkTitle(title: String) {
        Espresso.onView(ViewMatchers.withId(R.id.toolbar))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(title))))
    }

    fun inputAmountInRub(number: String) {
        val parts = number.split(",")
        Espresso.onView(withId(etAmountPrimaryInteger)).perform(
            ViewActions.replaceText(parts[0]),
            ViewActions.closeSoftKeyboard()
        )
        Espresso.onView(withId(etAmountPrimaryFloat)).perform(
            ViewActions.replaceText(parts[1]),
            ViewActions.closeSoftKeyboard()
        )
    }

    fun pressOnSave() {
        Espresso.onView(withId(R.id.menu_save)).perform(ViewActions.click())
        val new = AccountListScreen(device)
        new.waitForScreenToBeDisplayed()
    }

    fun tapOnCurrency(): SelectCurrencyDialogScreen {

        Espresso.onView(withId(currencyRv)).perform(ViewActions.click())
        val new = SelectCurrencyDialogScreen(device)
        new.waitForDialogToBeDisplayed()
        return new
    }

    fun checkCurrencyContainerDisplayed() {

//        currencyRv
//        TODO("Not yet implemented")
    }

    fun inputAmountInCurrency(number: String) {
        val parts = number.split(",")
        Espresso.onView(withId(etAmountSecondaryInteger)).perform(
                ViewActions.replaceText(parts[0]),
                ViewActions.closeSoftKeyboard()
        )
        Espresso.onView(withId(etAmountSecondaryFloat)).perform(
                ViewActions.replaceText(parts[1]),
                ViewActions.closeSoftKeyboard()
        )
    }

    fun checkSumInRub(number: String) {
        val parts = number.split(",")
        Espresso.onView(ViewMatchers.withId(etAmountPrimaryInteger))
                .check(ViewAssertions.matches(ViewMatchers.withText(parts[0])))

        Espresso.onView(ViewMatchers.withId(etAmountPrimaryFloat))
                .check(ViewAssertions.matches(ViewMatchers.withText(parts[1])))
    }

    fun inputRate(rate: String) {
        Espresso.onView(withId(etRate)).perform(
                ViewActions.replaceText(rate),
                ViewActions.closeSoftKeyboard()
        )
    }
}
