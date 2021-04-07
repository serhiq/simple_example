package com.gmail.uia059466.test_for_work_db.screen.dialog

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.uiautomator.UiDevice
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.utils.BaseScreen


class DeleteDialogScreen(on: UiDevice) : BaseScreen(on) {
    //   it is dialog without layout no content layout
    override val contentLayout: Int = 0
    fun waitForDialogToBeDisplayed() {
        Thread.sleep(300)
    }

    override fun checkDefaultLayout() {
        checkTextDisplayed(title)
    }

    fun clickDelete() {
        onView(ViewMatchers.withText(R.string.dialog_delete_user_button)).perform(click())
    }

    private val title = R.string.dialog_delete_user_title
    private val okButton = android.R.string.ok
}