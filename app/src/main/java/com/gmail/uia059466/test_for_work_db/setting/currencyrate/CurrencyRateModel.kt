package com.gmail.uia059466.test_for_work_db.setting.currencyrate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource
import com.gmail.uia059466.test_for_work_db.db.rates.RatesUser
import com.gmail.uia059466.test_for_work_db.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class CurrencyRateModel(
  val localDataSource: LocalDataSource
) : ViewModel() {

    private val _snackbarText = SingleLiveEvent<Int>()
    val snackbarText: LiveData<Int> = _snackbarText

    private val _lists = SingleLiveEvent<List<RatesUser>>()
    val lists: LiveData<List<RatesUser>> = _lists

    init {
        updateList()
    }

    private fun updateList() {
        viewModelScope.launch {
            val result = localDataSource.getAllUserRates()
            if (result is HolderResult.Success) {
                _lists.postValue(result.data)
            }
        }
    }

    fun deleteIfMay(rate: RatesUser) {
        viewModelScope.launch {
            val result = localDataSource.deleteRate(rate.id)
            if (result is HolderResult.Success) {
                updateList()
            }
        }
    }

    fun refreshList() {
        updateList()
    }
}