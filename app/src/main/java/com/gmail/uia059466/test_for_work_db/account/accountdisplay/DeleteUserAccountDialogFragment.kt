package com.gmail.uia059466.test_for_work_db.account.accountdisplay

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.gmail.uia059466.test_for_work_db.R

class DeleteUserAccountDialogFragment : AppCompatDialogFragment() {

    var onOk: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.dialog_delete_user_title))
                .setMessage(getString(R.string.dialog_delete_user_message))
                .setPositiveButton(getString(R.string.dialog_delete_user_button)) { _, _ ->
                    onOk?.invoke()
                }
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                }
        return builder.create()
    }
}