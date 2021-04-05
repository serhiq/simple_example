package com.gmail.uia059466.test_for_work_db.utls

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmail.uia059466.test_for_work_db.AppApplication
import com.gmail.uia059466.test_for_work_db.account.AccountsViewModel
import com.gmail.uia059466.test_for_work_db.account.accountdisplay.AccountDisplayViewModel
import com.gmail.uia059466.test_for_work_db.account.accountedit.AddEditAccountModel
import com.gmail.uia059466.test_for_work_db.setting.currency.CurrencyModel
import com.gmail.uia059466.test_for_work_db.setting.currencyrate.CurrencyRateModel
import com.gmail.uia059466.test_for_work_db.setting.rateedit.AddEditRateModel
import com.gmail.uia059466.test_for_work_db.transaction.TransactionViewModel
import com.gmail.uia059466.test_for_work_db.transaction.addedittransaction.AddEditTransactionViewModel

/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val a: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(CurrencyModel::class.java)     ->
                    CurrencyModel(
                            localDataSource = (a as AppApplication).localDataSource
                    )
                isAssignableFrom(CurrencyRateModel::class.java) ->
                    CurrencyRateModel(
                            localDataSource = (a as AppApplication).localDataSource
                    )

                isAssignableFrom(AddEditRateModel::class.java)    ->
                    AddEditRateModel(
                            localDataSource = (a as AppApplication).localDataSource
                    )
                isAssignableFrom(AddEditAccountModel::class.java) ->
                    AddEditAccountModel(
                            localDataSource = (a as AppApplication).localDataSource
                    )


                isAssignableFrom(AccountsViewModel::class.java) ->
                    AccountsViewModel(
                            localDataSource = (a as AppApplication).localDataSource
                    )

                isAssignableFrom(AccountDisplayViewModel::class.java) ->
                    AccountDisplayViewModel(
                            localDataSource = (a as AppApplication).localDataSource
                    )

                isAssignableFrom(TransactionViewModel::class.java) ->
                    TransactionViewModel(
                            localDataSource = (a as AppApplication).localDataSource
                    )
                isAssignableFrom(AddEditTransactionViewModel::class.java) ->
                    AddEditTransactionViewModel(
                            localDataSource = (a as AppApplication).localDataSource
                    )


                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}