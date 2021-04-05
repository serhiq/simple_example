package com.gmail.uia059466.test_for_work_db.transaction.addedittransaction

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.account.accountedit.UserAccount

class SelectAccountDialogFragment : AppCompatDialogFragment() {

    var onOk: (() -> Unit)? = null
    var selectedNow: UserAccount? = null
    val exsistLisAccount = mutableListOf<UserAccount>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val displayedList = exsistLisAccount.map { it.title }
        val checkedItem = if (selectedNow != null) {
            val result = exsistLisAccount.indexOf(selectedNow)
            if (result < 0) 1 else result
        } else {
            0
        }

        return AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.dialog_select_account))
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                }
                .setSingleChoiceItems(
                        displayedList.toTypedArray(),
                        checkedItem
                ) { dialog, position ->
                    selectedNow = exsistLisAccount[position]
                    onOk?.invoke()
                    dialog.dismiss()
                }
                .create()
    }
}