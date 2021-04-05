package com.gmail.uia059466.test_for_work_db.account.accountdisplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.uia059466.test_for_work_db.account.accountedit.UserAccount
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource
import com.gmail.uia059466.test_for_work_db.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class AccountDisplayViewModel(
        val localDataSource: LocalDataSource
) : ViewModel() {

    private val _account = SingleLiveEvent<UserAccount>()
    val account: LiveData<UserAccount> = _account
    private var currentAccountId: Long = 0

    private val _runBack = SingleLiveEvent<Boolean>()
    val runBack: LiveData<Boolean> = _runBack

    fun start(idAccount: Long) {
        if (idAccount == 0L) {

        } else {
            currentAccountId = idAccount
            loadAccount(idAccount)
        }
    }

    private fun loadAccount(idAccount: Long) {
        viewModelScope.launch {
            val result = localDataSource.getAccountById(idAccount)
            if (result is HolderResult.Success) {
                _account.postValue(result.data)
            }
        }
    }

    fun deleteUserAccount() {
        viewModelScope.launch {
            localDataSource.deleteAccount(currentAccountId)
            _runBack.postValue(true)
        }
    }

    fun refresh() {
        loadAccount(currentAccountId)
    }
}