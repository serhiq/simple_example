package com.gmail.uia059466.test_for_work_db.transaction.addedittransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.account.accountedit.UserAccount
import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.db.LocalDataSource
import com.gmail.uia059466.test_for_work_db.db.currency.UserCurrency
import com.gmail.uia059466.test_for_work_db.db.transaction.TransactionEditTextState
import com.gmail.uia059466.test_for_work_db.db.transaction.TypeOperation
import com.gmail.uia059466.test_for_work_db.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*

class AddEditTransactionViewModel(
        val localDataSource: LocalDataSource
) : ViewModel() {

    private val _snackbarText = SingleLiveEvent<Int>()
    val snackbarText: LiveData<Int> = _snackbarText

    private val _transactionD = SingleLiveEvent<TransactionSingleDisplay>()
    val transactionD: LiveData<TransactionSingleDisplay> = _transactionD

    private val _stateEditTextState = SingleLiveEvent<TransactionEditTextState>()
    val stateEditTextState: LiveData<TransactionEditTextState> = _stateEditTextState

    private val _stateRate = SingleLiveEvent<String>()
    val stateRate: LiveData<String> = _stateRate

    private val _isVisibleEditMode = SingleLiveEvent<Boolean>()
    val isVisibleEditMode: LiveData<Boolean> = _isVisibleEditMode

    private val _runBack = SingleLiveEvent<Boolean>()
    val runBack: LiveData<Boolean> = _runBack

    private var idTransaction = 0L
    private var idAccount = 0L

    val listAccount = mutableListOf<UserAccount>()
    val listCurrency = mutableListOf<UserCurrency>()
    private var codeOperation = TypeOperation.OUTCOME.code


    init {
        viewModelScope.launch {
            val accounts = localDataSource.getAllAccounts()
            val result = localDataSource.getAllUserCurrencies()
            if (result is HolderResult.Success) {
                listCurrency.addAll(result.data)

            }
            if (accounts is HolderResult.Success) {
                listAccount.addAll(accounts.data)
                if (idTransaction == 0L) {
                    val selectedAccount = listAccount.find { it.id == idAccount }
                            ?: listAccount.first()


                    _transactionD.postValue(TransactionSingleDisplay(
                            id = 0, codeOperation, account = selectedAccount, date = Date(), note = "", amount = BigDecimal.ZERO, fromAmount = BigDecimal.ZERO, fromCurrency = Currency.getInstance("RUB").currencyCode))

                    _stateEditTextState.postValue(TransactionEditTextState(
                            isUseCurrency = false,
                            amountRub = "0",
                            amountKop = "0",
                            amountCurrency = "0",
                            amountOst = "0",
                            comment = ""))
                }

            }
        }
    }


    fun start(id: Long,
              operation: String,
              bundleIdAccount: Long) {
        idAccount = bundleIdAccount
        idTransaction = id
        codeOperation = operation
        if (idTransaction == 0L) {

            _isVisibleEditMode.postValue(false)
        } else {

            _isVisibleEditMode.postValue(true)
//      loadAccount(idTransaction)
        }
    }

    fun selectCurrency(selectedNow: String?) {
        if (selectedNow != null) {
            val old = _transactionD.value
            if (old != null) {
                val new = old.copy(fromCurrency = selectedNow)
                _transactionD.postValue(new)
                updateRate(selectedNow, old.date)
            }
        }
    }

    private fun updateRate(selectedNow: String,
                           date: Date) {


        viewModelScope.launch {
            val result = localDataSource.getRate(selectedNow, date)
            if (result is HolderResult.Success) {
                _stateRate.postValue(result.data)
            }
        }
    }


    fun selectDate(date: Long) {
        val newDate = Date(date)
        val old = _transactionD.value
        if (old != null) {
            val new = old.copy(date = newDate)
            _transactionD.postValue(new)
        }
    }

    fun trySave(state: TransactionEditTextState) {
        if (!state.isCorrectState()) {
            _snackbarText.postValue(R.string.summ_zero)
        } else {
            save(state)
        }
    }

    private fun save(state: TransactionEditTextState) {
        val old = _transactionD.value
        if (old != null) {


            val new = old.copy(amount = state.requestAmoutInKop(), fromAmount = state.requestCurrencyAmount(), note = state.comment
                    ?: " ")
            when (idTransaction == 0L) {
                true -> insertNewTransaction(new)
//      false -> updateAccount(new)
            }
        }
    }

    private fun insertNewTransaction(new: TransactionSingleDisplay) {
        viewModelScope.launch {
            localDataSource.addTransaction(new.asTransactionDataWithNewAccount())
            _runBack.postValue(true)
        }
    }

    fun updateEditTextStateT(old: TransactionEditTextState,
                             codeCurrency: String) {
        if (codeCurrency == Currency.getInstance("RUB").currencyCode) {
            val new = old.copy(isUseCurrency = false, amountCurrency = BigDecimal.ZERO.toPlainString(), amountOst = BigDecimal.ZERO.toPlainString())
            _stateEditTextState.postValue(new)
            _stateRate.postValue("1")
        } else {
            val new = old.copy(isUseCurrency = true, amountCurrency = BigDecimal.ZERO.toPlainString(), amountOst = BigDecimal.ZERO.toPlainString())
            _stateEditTextState.postValue(new)
            _stateRate.postValue("1")
        }
    }

    fun selectAccount(newAccount: UserAccount?) {
        val old = _transactionD.value
        if (old != null && newAccount != null) {
            val new = old.copy(account = newAccount)
            _transactionD.postValue(new)
        }
    }
}