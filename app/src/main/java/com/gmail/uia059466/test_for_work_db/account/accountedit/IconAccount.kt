package com.gmail.uia059466.test_for_work_db.account.accountedit

import com.gmail.uia059466.test_for_work_db.R

enum class IconAccount(val code: String,val resId:Int) {
  CREDIT_CARD("cr", R.drawable.ic_account_icon_credit_card),
  CASH("ch", R.drawable.ic_account_cash),
  COIN("coin", R.drawable.ic_account_coin),
  WALLET_OUTLINE("wo", R.drawable.ic_account_outline_wallet),
  GOLD("g", R.drawable.ic_account_gold),
  MONEY("m", R.drawable.ic_account_money),
  PIG_OUTLINE("po", R.drawable.ic_account_pig_outline),
  PIG("pi", R.drawable.ic_account_pig_solid),
  SAFE_OUTLINE("sao", R.drawable.ic_account_safe_outline),
  SAFE("sa", R.drawable.ic_account_safe_solid),
  VIZA("viza", R.drawable.ic_account_viza),
  WALLET("wall", R.drawable.ic_account_wallet);

  companion object {
    fun fromString(raw: String): IconAccount {
      return IconAccount.values().single { it.code == raw }
    }
    
    fun toString(value: IconAccount): String = value.code

    fun requestDefault(): IconAccount {
      return CREDIT_CARD
    }
  }
}
