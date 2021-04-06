package com.gmail.uia059466.test_for_work_db.screen

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.uiautomator.UiDevice
import com.gmail.uia059466.test_for_work_db.utils.BaseScreen
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.utils.TestUtils
import com.gmail.uia059466.test_for_work_db.utils.TestUtils.withRecyclerView

class AccountListScreen(on: UiDevice) : BaseScreen(on) {
    
    override val contentLayout: Int= R.id.accounts_list_content
    private val list = R.id.list

    override fun checkDefaultLayout() {
        checkViewDisplayed(contentLayout)
    }

    fun checkTitle(title:String){
        Espresso.onView(withId(R.id.toolbar))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(title))))
    }

    fun checkSizeRecycler(size:Int)  {
        Espresso.onView(withId(list))
            .check(ViewAssertions.matches(ViewMatchers.hasChildCount(size)))
    }
    fun clickOnList(position: Int) {
        Espresso.onView(withRecyclerView(list).atPosition(position)).perform(ViewActions.click());    }


    fun checkItemView(position: Int, title: String, amount:String) {
        Espresso.onView(
            TestUtils.withRecyclerView(list).atPositionOnView(position, R.id.title_account_tv)
        ).check(
            ViewAssertions.matches(ViewMatchers.withText(title))
        )

        Espresso.onView(
            TestUtils.withRecyclerView(list).atPositionOnView(position, R.id.amount_tv)
        ).check(
            ViewAssertions.matches(ViewMatchers.withText(amount))
        )
    }

    fun openAddAccount(): AddEditAccountScreen {
        Espresso.onView(withId(R.id.menu_add)).perform(ViewActions.click())
        val new=AddEditAccountScreen(device)
        new.waitForScreenToBeDisplayed()
        return new
    }

}