package com.gmail.uia059466.test_for_work_db.db

sealed class HolderResult<out R>: IResultDbCommand {

    data class Success<out T>(val data: T) : HolderResult<T>()
    data class Error(val exception: Exception) : HolderResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

val HolderResult<*>.succeeded
    get() = this is HolderResult.Success && data != null
