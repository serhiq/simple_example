package com.gmail.uia059466.test_for_work_db.account.accountedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource
import com.gmail.uia059466.test_for_work_db.db.account.AddUserAccount
import com.gmail.uia059466.test_for_work_db.db.account.UpdateUserAccount
import com.gmail.uia059466.test_for_work_db.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import java.math.BigDecimal

class AddEditAccountModel(
        val localDataSource: LocalDataSource
) : ViewModel() {

    private val _snackbarText = SingleLiveEvent<Int>()
    val snackbarText: LiveData<Int> = _snackbarText

    private val _account = SingleLiveEvent<UserAccount>()
    val account: LiveData<UserAccount> = _account

    private val _titleAccount = SingleLiveEvent<String>()
    val titleAccount: LiveData<String> = _titleAccount


    private val _isVisibleEditMode = SingleLiveEvent<Boolean>()
    val isVisibleEditMode: LiveData<Boolean> = _isVisibleEditMode

    private val _runBack = SingleLiveEvent<Boolean>()
    val runBack: LiveData<Boolean> = _runBack

    var idAccount = 0L

    fun start(id: Long) {
        idAccount = id
        if (idAccount == 0L) {
            _account.postValue(UserAccount(id = 0, title = "", iconAccount = IconAccount.CREDIT_CARD, BigDecimal.ZERO))

            _isVisibleEditMode.postValue(false)
        } else {

            _isVisibleEditMode.postValue(true)
            loadAccount(idAccount)
        }
    }

    private fun loadAccount(idAccount: Long) {
        viewModelScope.launch {
            val result = localDataSource.getAccountById(idAccount)
            if (result is HolderResult.Success) {
                _account.postValue(result.data)
                _titleAccount.postValue(result.data.title)
            }
        }
    }


    fun trySave(title: String) {
        if (title.isBlank()) {
            _snackbarText.postValue(R.string.no_correct_title_account)
        } else {
            save(title)
        }
    }

    private fun save(title: String) {
        val old = _account.value
        if (old != null) {

            val new = old.copy(title = title)
            when (idAccount == 0L) {
              true -> insertNewAccount(new)
              false -> updateAccount(new)
            }
        }
    }

    private fun updateAccount(new: UserAccount) {
        viewModelScope.launch {
            localDataSource.execute(UpdateUserAccount(new))
            _runBack.postValue(true)
        }
    }

    private fun insertNewAccount(new: UserAccount) {
        viewModelScope.launch {
            localDataSource.execute(AddUserAccount(new))
            _runBack.postValue(true)
        }
    }

    fun selectIcon(newIcon: IconAccount) {
        val old = _account.value
        if (old != null) {
            val new = old.copy(iconAccount = newIcon)
            _account.postValue(new)
        }
    }
}