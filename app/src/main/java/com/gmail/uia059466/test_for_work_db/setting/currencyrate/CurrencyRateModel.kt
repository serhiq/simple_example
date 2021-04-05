package com.gmail.uia059466.test_for_work_db.setting.currencyrate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.db.currency.AddUserCurrency
import com.gmail.uia059466.test_for_work_db.db.currency.UserCurrency
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource
import com.gmail.uia059466.test_for_work_db.db.rates.DeleteRates
import com.gmail.uia059466.test_for_work_db.db.rates.RatesUser
import com.gmail.uia059466.test_for_work_db.utls.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.*

class CurrencyRateModel(
        val localDataSource: LocalDataSource
  ) : ViewModel() {
//
  private val _snackbarText = SingleLiveEvent<Int>()
  val snackbarText: LiveData<Int> = _snackbarText
//
//  var _sortOrder:SortOrder=prefs.readSortOrderList()
//  val navigateToManualSort = SingleLiveEvent<Boolean>()

  private val _lists = SingleLiveEvent<List<RatesUser>>()
  val lists: LiveData<List<RatesUser>> = _lists

//  val navigateToEditList = SingleLiveEvent<Long>()





  init {
    updateList()
  }

  private fun updateList(){
    viewModelScope.launch {
      val result = localDataSource.getAllUserRates()
      if (result is HolderResult.Success){
        _lists.postValue(result.data)
      }else{
//        todo show snakbar error load
      }
    }
  }

  fun insertInDbAndUpdateList(selectedNow: Currency) {
    viewModelScope.launch {
     localDataSource.execute(AddUserCurrency(selectedNow))
      updateList()
    }
  }

//  todo не работает
  fun deleteIfMay(rate: RatesUser) {
  viewModelScope.launch {
    val result = localDataSource.deleteRate(rate.id)
    if (result is HolderResult.Success){
      updateList()
    }else{
//        todo по данной валюте есть операции, ее нельзя удалить?!
    }
  }


//    viewModelScope.launch {
//      val result = localDataSource.execute(DeleteRates(rate.id))
//      if (result is HolderResult.Success){
//         updateList()
//     }else{
////        todo по данной валюте есть операции, ее нельзя удалить?!
//      }
//    }
  }

  fun refreshList() {
    updateList()
  }
}