package com.gmail.uia059466.test_for_work_db.screen

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.uiautomator.UiDevice
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.utils.BaseScreen

class AccountDisplayScreen(on: UiDevice) : BaseScreen(on) {

    override val contentLayout: Int = R.id.account_display_content
    private val amountTv = R.id.amount_tv
    private val deleteFl = R.id.delete_fl
    private val editFl = R.id.edit_fl
    private val incomeLl = R.id.income_ll
    private val outcomeLl = R.id.outcome_ll

    override fun checkDefaultLayout() {
        checkViewDisplayed(contentLayout)
    }

    fun checkTitle(title: String) {
        Espresso.onView(withId(R.id.toolbar))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(title))))
    }

    fun clickOnDelete() {
        Espresso.onView(withId(deleteFl)).perform(ViewActions.click())
    }

    fun clickOnEdit() {
        Espresso.onView(withId(editFl)).perform(ViewActions.click())

    }

    fun clickOnIncome(): TransactionScreen {
        Espresso.onView(withId(incomeLl)).perform(ViewActions.click())
        val new = TransactionScreen(device)
        new.waitForScreenToBeDisplayed()
        return new
    }

    fun clickOnOutcome(): TransactionScreen {
        Espresso.onView(withId(outcomeLl)).perform(ViewActions.click())
        val new = TransactionScreen(device)
        new.waitForScreenToBeDisplayed()
        return new
    }

    fun checkAmount(amount: String) {
        Espresso.onView(withId(amountTv))
            .check(ViewAssertions.matches(ViewMatchers.withText(amount)))

    }
}