package com.gmail.uia059466.test_for_work_db.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource
import com.gmail.uia059466.test_for_work_db.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class TransactionViewModel(
        val localDataSource: LocalDataSource
) : ViewModel() {
    private val _snackbarText = SingleLiveEvent<Int>()
    val snackbarText: LiveData<Int> = _snackbarText

    private val _lists = SingleLiveEvent<List<DisplayTransaction>>()
    val lists: LiveData<List<DisplayTransaction>> = _lists

    init {
        updateList()
    }

    private fun updateList() {
        viewModelScope.launch {
            val result = localDataSource.getAllTransactionDisplay()
            if (result is HolderResult.Success) {
                _lists.postValue(result.data)
            }
        }
    }

    fun refreshList() {
        updateList()
    }
}