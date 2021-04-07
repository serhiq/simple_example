package com.gmail.uia059466.test_for_work_db.screen.dialog

import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.uiautomator.UiDevice
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.utils.BaseScreen
import org.hamcrest.Matchers
import org.hamcrest.core.IsInstanceOf


class SelectDateDialogScreen(on: UiDevice) : BaseScreen(on) {

    //   it is dialog without layout no content layout
    override val contentLayout: Int = 0


    fun waitForDialogToBeDisplayed() {
        Thread.sleep(300)
    }


    private val title = R.string.dialog_select_currency_transaction

    fun selectDate(day: Int) {
        val textView = onView(
            Matchers.allOf(
                ViewMatchers.withText(day.toString()),
                ViewMatchers.withParent(
                    Matchers.allOf(
                        withId(R.id.month_grid),
                        ViewMatchers.withParent(IsInstanceOf.instanceOf(LinearLayout::class.java))
                    )
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textView.perform(click())


        val materialButton = onView(
            Matchers.allOf(
                withId(R.id.confirm_button), ViewMatchers.withText("OK"),
                ViewMatchers.isDisplayed()
            )
        )
        materialButton.perform(click())
    }

    override fun checkDefaultLayout() {
        checkTextDisplayed(title)
    }
}
