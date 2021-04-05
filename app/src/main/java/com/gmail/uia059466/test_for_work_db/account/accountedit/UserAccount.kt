package com.gmail.uia059466.test_for_work_db.account.accountedit

import java.math.BigDecimal

data class UserAccount(
        val id:Long=0L,
        val title:String,
        val iconAccount: IconAccount,
        val amount:BigDecimal
)

