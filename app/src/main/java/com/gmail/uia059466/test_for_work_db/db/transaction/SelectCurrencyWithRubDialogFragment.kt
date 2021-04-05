package com.gmail.uia059466.test_for_work_db.db.transaction

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.gmail.uia059466.test_for_work_db.R

class SelectCurrencyWithRubDialogFragment : AppCompatDialogFragment() {

    var onOk: (() -> Unit)? = null
     var selectedNow: String?=null
    val exsistListCurrency= mutableListOf<String>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val checkedItem = if (selectedNow != null) {
            val result=exsistListCurrency.indexOf(selectedNow)
            if (result<0) 1 else result
        } else {
            0
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_select_currency_transaction))
            .setNegativeButton(android.R.string.cancel) { _, _ ->
            }
            .setSingleChoiceItems(
                    exsistListCurrency.toTypedArray(),
                    checkedItem
            ) { dialog, position ->
                selectedNow = exsistListCurrency[position]
                onOk?.invoke()
                dialog.dismiss()
            }
            .create()
    }
}