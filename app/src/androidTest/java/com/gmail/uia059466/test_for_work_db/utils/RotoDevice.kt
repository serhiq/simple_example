package com.gmail.uia059466.test_for_work_db.utils

import androidx.test.uiautomator.UiDevice
import com.gmail.uia059466.test_for_work_db.screen.AccountListScreen
import com.gmail.uia059466.test_for_work_db.screen.AddEditAccountScreen


class RotoDevice(val device: UiDevice) {
  

  
  fun checkOpen(type: typeScreen) {
    val isDisplayed=when(type){
//      RotoDevice.type.WELCOME ->WelcomeScreen(on = device).isShow()
//      RotoDevice.type.SIGN_UP ->SignUpScreen(on = device).isShow()
//      RotoDevice.type.SIGN_IN ->SignInScreen(on = device).isShow()
//      RotoDevice.type.DIALOG_SELECT_THEME -> SelectThemeScreen(device).isShow()
//      RotoDevice.type.ACCOUNT -> AccountScreen(device).isShow()
//      RotoDevice.type.RESET_PASSWORD -> ResetPasswordScreen(device).isShow()
//      RotoDevice.type.DIALOG_SUSSED_RESET -> SucceedResetPasswordScreen(device).isShow()
//      RotoDevice.type.DIALOG_DELETE_ACCOUNT -> DeleteDialogScreen(device).isShow()
//      RotoDevice.type.DIALOG_SIGN_OUT -> SignOutDialogScreen(device).isShow()
      typeScreen.AccountList -> AccountListScreen(device).isShow()
      typeScreen.AddEditAccount -> AddEditAccountScreen(device).isShow()
    }
  }
  
  enum class typeScreen{
    AccountList, AddEditAccount;
  }
}