package com.gmail.uia059466.test_for_work_db.screen

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.uiautomator.UiDevice
import com.gmail.uia059466.test_for_work_db.utils.BaseScreen
import com.gmail.uia059466.test_for_work_db.R

class AddEditAccountScreen(on: UiDevice) : BaseScreen(on) {

    override val contentLayout: Int = R.id.add_edit_account_content

    private val caption = R.id.text
    private val etTitleAccount = R.id.title_account_et
    private val iconFl = R.id.icon_fl


    override fun checkDefaultLayout() {

    }

    fun checkTitle(title: String) {
        Espresso.onView(ViewMatchers.withId(R.id.toolbar))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(title))))
    }


    fun inputTitle(title: String) {
        Espresso.onView(withId(etTitleAccount)).perform(
            ViewActions.replaceText(title),
            ViewActions.closeSoftKeyboard()
        )
    }

    fun tapOnIcon() {
        Espresso.onView(ViewMatchers.withId(iconFl)).perform(ViewActions.click())
    }

    fun pressOnSave() {
        Espresso.onView(withId(R.id.menu_save)).perform(ViewActions.click())
        val new = AccountListScreen(device)
        new.waitForScreenToBeDisplayed()
    }
}
