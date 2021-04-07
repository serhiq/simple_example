package com.gmail.uia059466.test_for_work_db.screen.dialog

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.uiautomator.UiDevice
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.utils.BaseScreen

class SelectCurrencyDialogScreen(on: UiDevice) : BaseScreen(on) {

  //   it is dialog without layout no content layout
  override val contentLayout: Int = 0


  fun waitForDialogToBeDisplayed() {
    waitForViewToBeDisplayed(ViewMatchers.withText(title), 3_000)
  }


  private val title= R.string.dialog_select_currency_transaction
//  private val listView = on.findObject(UiSelector().resourceId("$id/select_dialog_listview"))
  
//  private val numberOfSort: Int
//    get() {
//      return listView.childCount
//    }
//
  fun selectCurrency (codeCurrency:String) {
    Espresso.onView(ViewMatchers.withText(codeCurrency)).perform(ViewActions.click())
  }
  
  override fun checkDefaultLayout() {
    checkTextDisplayed(title)
  }
}
