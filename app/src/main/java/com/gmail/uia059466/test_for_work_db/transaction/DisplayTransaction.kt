package com.gmail.uia059466.test_for_work_db.transaction

sealed class DisplayTransaction {
    class TransactionDisplay(
            val title: String = "title",
            val amount: String = "info",
            val id: Long = 0L,
            val amountCurrency: String = ""
    ) : DisplayTransaction()

    class Header(
            val title: String
    ) : DisplayTransaction()
}
