package com.gmail.uia059466.test_for_work_db.db

import android.content.Context
import com.gmail.uia059466.test_for_work_db.account.accountedit.UserAccount
import com.gmail.uia059466.test_for_work_db.db.account.DeleteAccount
import com.gmail.uia059466.test_for_work_db.db.account.GetAllAccounts
import com.gmail.uia059466.test_for_work_db.db.account.GetUserAccountById
import com.gmail.uia059466.test_for_work_db.db.account.UpdateUserAccount
import com.gmail.uia059466.test_for_work_db.db.currency.DeleteCurrency
import com.gmail.uia059466.test_for_work_db.db.currency.GetAllUserCurrencyNotCommand
import com.gmail.uia059466.test_for_work_db.db.currency.UserCurrency
import com.gmail.uia059466.test_for_work_db.db.rates.DeleteRates
import com.gmail.uia059466.test_for_work_db.db.rates.GetAllRates
import com.gmail.uia059466.test_for_work_db.db.rates.GetLastRateByCurrencyAndDate
import com.gmail.uia059466.test_for_work_db.db.rates.RatesUser
import com.gmail.uia059466.test_for_work_db.db.transaction.AddUserTransaction
import com.gmail.uia059466.test_for_work_db.db.transaction.CreateListTransactionWithCaption
import com.gmail.uia059466.test_for_work_db.db.transaction.GetAllTransactionDatabase
import com.gmail.uia059466.test_for_work_db.transaction.DisplayTransaction
import com.gmail.uia059466.test_for_work_db.transaction.addedittransaction.TransactionDataWithNewAccount
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

class LocalDataSource(context: Context) {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    val db =AppDatabaseHelper.getInstance(context)
    private val runner= DataBaseRunner()

    fun resetDb(){
        db.reset()
    }

    suspend fun execute(command: IDbCommand): IResultDbCommand {
      return  runner.afterPrevious {
            try {
                return@afterPrevious command.execute(db)
            }catch (e: Exception){
                return@afterPrevious HolderResult.Error(e)
            }finally {
            }
        }
    }

    suspend fun getAllUserCurrencies():HolderResult<List<UserCurrency>>{
        return  withContext(ioDispatcher){
              runner.afterPrevious {
                try {
                    return@afterPrevious GetAllUserCurrencyNotCommand().execute(db)
                }catch (e: Exception){
                    return@afterPrevious HolderResult.Error(e)
                }finally {
                }
            }
        }
   }

    suspend fun deleteCurrency(id: Long): HolderResult<Int> {
        return  withContext(ioDispatcher){
            runner.afterPrevious {
                try {
                    return@afterPrevious DeleteCurrency(id).execute(db)
                }catch (e: Exception){
                    return@afterPrevious HolderResult.Error(e)
                }finally {
                }
            }
        }
    }


    suspend fun getAllUserRates():HolderResult<List<RatesUser>>{
        return  withContext(ioDispatcher){
            runner.afterPrevious {
                try {
                    return@afterPrevious GetAllRates().execute(db)
                }catch (e: Exception){
                    return@afterPrevious HolderResult.Error(e)
                }finally {
                }
            }
        }
    }

    suspend fun deleteRate(id: Long): HolderResult<Int> {
        return  withContext(ioDispatcher){
            runner.afterPrevious {
                try {
                    return@afterPrevious DeleteRates(id).execute(db)
                }catch (e: Exception){
                    return@afterPrevious HolderResult.Error(e)
                }finally {
                }
            }
        }
    }



//    Accounts

    suspend fun getAllAccounts():HolderResult<List<UserAccount>>{
        return  withContext(ioDispatcher){
            runner.afterPrevious {
                try {
                    return@afterPrevious GetAllAccounts().execute(db)
                }catch (e: Exception){
                    return@afterPrevious HolderResult.Error(e)
                }finally {
                }
            }
        }
    }
    suspend fun getAllTransactionDisplay():HolderResult<List<DisplayTransaction>>{
        return  withContext(ioDispatcher){
            runner.afterPrevious {
                try {
                   val result= GetAllTransactionDatabase().execute(db)
                    when (result){
                     is HolderResult.Success->  return@afterPrevious CreateListTransactionWithCaption().execute(result.data)
                     is HolderResult.Error->  return@afterPrevious HolderResult.Error(result.exception)
                    }
                }catch (e: Exception){
                    return@afterPrevious HolderResult.Error(e)
                }finally {
                }
            }
        }
    }

    suspend fun getAccountById(idAccount: Long): HolderResult<UserAccount> {
        return  withContext(ioDispatcher){
            runner.afterPrevious {
                try {
                    return@afterPrevious GetUserAccountById(idAccount).execute(db)
                }catch (e: Exception){
                    return@afterPrevious HolderResult.Error(e)
                }finally {
                }
            }
        }
    }



    suspend fun addTransaction(transaction: TransactionDataWithNewAccount): HolderResult<Long> {
        return  withContext(ioDispatcher){
            runner.afterPrevious {
                try {
                    UpdateUserAccount(transaction.newAccount).execute(db)
                    return@afterPrevious AddUserTransaction(transaction.transactionDb).execute(db)
                }catch (e: Exception){
                    return@afterPrevious HolderResult.Error(e)
                }finally {
                }
            }
        }
    }

    suspend fun deleteAccount(accountId: Long):HolderResult<Int> {
        return  withContext(ioDispatcher){
            runner.afterPrevious {
                try {
                    return@afterPrevious DeleteAccount(accountId).execute(db)
                }catch (e: Exception){
                    return@afterPrevious HolderResult.Error(e)
                }finally {
                }
            }
        }
    }

  suspend  fun getRate(selectedNow: String,
                   date: Date): HolderResult<String> {
        return  withContext(ioDispatcher){
            runner.afterPrevious {
                try {
                    return@afterPrevious GetLastRateByCurrencyAndDate(selectedNow,date).execute(db)
                }catch (e: Exception){
                    return@afterPrevious HolderResult.Error(e)
                }finally {
                }
            }
        }
    }

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(context: Context): LocalDataSource {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = LocalDataSource(context)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


}

interface IResultDbCommand{}
interface IDbCommand{
    fun execute(db:AppDatabaseHelper):IResultDbCommand
}