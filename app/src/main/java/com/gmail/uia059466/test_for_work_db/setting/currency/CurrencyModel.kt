package com.gmail.uia059466.test_for_work_db.setting.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource
import com.gmail.uia059466.test_for_work_db.db.currency.AddUserCurrency
import com.gmail.uia059466.test_for_work_db.db.currency.UserCurrency
import com.gmail.uia059466.test_for_work_db.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.*

class CurrencyModel(
        val localDataSource: LocalDataSource
  ) : ViewModel() {
    private val _isVisibleEditMode = SingleLiveEvent<Boolean>()
    val isVisibleEditMode: LiveData<Boolean> = _isVisibleEditMode

  private val _snackbarText = SingleLiveEvent<Int>()
  val snackbarText: LiveData<Int> = _snackbarText

  private val _lists = SingleLiveEvent<List<UserCurrency>>()
  val lists: LiveData<List<UserCurrency>> = _lists

  init {
    updateList()
  }

  private fun updateList(){
    viewModelScope.launch {
      val result = localDataSource.getAllUserCurrencies()
      if (result is HolderResult.Success){
        _lists.postValue(result.data)
        _isVisibleEditMode.postValue(result.data.size>1)
      }
    }
  }

  fun insertInDbAndUpdateList(selectedNow: Currency) {
    viewModelScope.launch {
     localDataSource.execute(AddUserCurrency(selectedNow))
      updateList()
    }
  }

    fun deleteIfMay(currency: UserCurrency) {
        if (currency.isLockedCurrency()) {
            _snackbarText.postValue(R.string.no_delete_rub)
            return
        }

    viewModelScope.launch {
      val result = localDataSource.deleteCurrency(currency.id)
      if (result is HolderResult.Success){
         updateList()
     }
    }
  }
}