package com.gmail.uia059466.test_for_work_db.setting.rateedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource
import com.gmail.uia059466.test_for_work_db.db.rates.AddUserRate
import com.gmail.uia059466.test_for_work_db.db.rates.RatesUser
import com.gmail.uia059466.test_for_work_db.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*

class AddEditRateModel(
        val localDataSource: LocalDataSource
) : ViewModel() {

    private val _snackbarText = SingleLiveEvent<Int>()
    val snackbarText: LiveData<Int> = _snackbarText
    private val _lists = SingleLiveEvent<List<String>>()
    val lists: LiveData<List<String>> = _lists

    private val _rateLiveDate = SingleLiveEvent<RatesUser>()
    val rateLiveDate: LiveData<RatesUser> = _rateLiveDate

    private val _runBack = SingleLiveEvent<Boolean>()
    val runBack: LiveData<Boolean> = _runBack

    init {
        updateList()
    }

    private fun updateList() {
        viewModelScope.launch {
            val result = localDataSource.getAllUserCurrencies()
            if (result is HolderResult.Success) {
                _lists.postValue(result.data.map { it.code })
//        // создаем новую с валютой первой в списке но не рублем
                _rateLiveDate.postValue(RatesUser(id = 0, currency = Currency.getInstance(result.data[1].code), rate = BigDecimal.ZERO, data = Date()))

            }
        }
    }

    fun selectCurrency(code: String?) {
        val newCurrency = Currency.getInstance(code)
        val old = _rateLiveDate.value

        if (newCurrency != null && old != null) {

            val new = old.copy(currency = newCurrency)
            _rateLiveDate.postValue(new)
        }
    }

    fun selectDate(date: Long) {
        val newDate = Date(date)
        val old = _rateLiveDate.value

        if (newDate != null && old != null) {

            val new = old.copy(data = newDate)
            _rateLiveDate.postValue(new)
        }

    }

    fun trySave(rate: BigDecimal?) {
        if (rate == null) {
            _snackbarText.postValue(R.string.no_correct_number_format)
            return
        } else if (rate == BigDecimal.ZERO) {
            _snackbarText.postValue(R.string.no_correct_rate)
            return
        } else {

            val old = _rateLiveDate.value
            if (old != null) {
                val new = old.copy(rate = rate)
                viewModelScope.launch {
                    localDataSource.execute(AddUserRate(new))
                    _runBack.postValue(true)
                }
            }
        }
    }
}