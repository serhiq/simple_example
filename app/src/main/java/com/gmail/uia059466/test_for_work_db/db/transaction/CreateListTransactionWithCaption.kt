package com.gmail.uia059466.test_for_work_db.db.transaction

import com.gmail.uia059466.test_for_work_db.db.HolderResult
import com.gmail.uia059466.test_for_work_db.transaction.DisplayTransaction
import com.gmail.uia059466.test_for_work_db.transaction.TransactionDataBase
import com.gmail.uia059466.test_for_work_db.transaction.asDisplayTransaction

class CreateListTransactionWithCaption {
    suspend fun execute(rawList:List<TransactionDataBase>): HolderResult<List<DisplayTransaction>> {
        val list: MutableList<DisplayTransaction> = mutableListOf<DisplayTransaction>()
        if (rawList.isEmpty()) return HolderResult.Success(list)
        var headerStr=""
        for (t in rawList){
            val newHeaderStr=t.createCaptionHeader()
            if (headerStr!=newHeaderStr){
                list.add(DisplayTransaction.Header(newHeaderStr))
                headerStr=newHeaderStr
            }
            list.add(t.asDisplayTransaction())
        }

        return HolderResult.Success(list)
    }
}

