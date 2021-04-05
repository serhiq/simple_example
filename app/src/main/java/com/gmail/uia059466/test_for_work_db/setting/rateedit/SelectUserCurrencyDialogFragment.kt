package com.gmail.uia059466.test_for_work_db.setting.rateedit

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.gmail.uia059466.test_for_work_db.R

class SelectUserCurrencyDialogFragment : AppCompatDialogFragment() {

    var onOk: (() -> Unit)? = null
     var selectedNow: String?=null
    val exsistListCurrency= mutableListOf<String>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        exsistListCurrency.remove("RUB")
        val checkedItem = if (selectedNow != null) {
            val result=exsistListCurrency.indexOf(selectedNow)
            if (result<0) 1 else result
        } else {
            0
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_select_currency_in))
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