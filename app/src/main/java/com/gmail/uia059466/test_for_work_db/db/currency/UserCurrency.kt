package com.gmail.uia059466.test_for_work_db.db.currency

class UserCurrency(
        val id: Long,
        val code: String,
        val description: String
) {
    fun isLockedCurrency(): Boolean {
        return code == "RUB"
    }
}