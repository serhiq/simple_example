package com.gmail.uia059466.test_for_work_db.account.accountedit

import android.app.Dialog

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.uia059466.test_for_work_db.R

class SelectIconAccountDialogFragment : AppCompatDialogFragment(), AccountIconAdapter.IconListener {

    var onOk: (() -> Unit)? = null
    lateinit var selectedNow: IconAccount

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.layout_avatar_bottom_sheet, null)
        val avatarRecyclerView = view?.findViewById<RecyclerView>(R.id.avatarRecyclerView)

        avatarRecyclerView?.layoutManager = GridLayoutManager(context, 5)

        val current = arguments?.getString(EXSTRA_ICON)

        val selected = when {
            current != null -> IconAccount.fromString(current)
            else            -> IconAccount.CREDIT_CARD
        }


        avatarRecyclerView?.adapter = AccountIconAdapter(selected, this)

        return AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.dialog_select_icon_account_title))
                .setView(view)
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                }
                .create()
    }


    companion object {
        private const val EXSTRA_ICON = "selected_icon"

        fun newInstance(currentIconAccount: IconAccount): SelectIconAccountDialogFragment {
            val dialog = SelectIconAccountDialogFragment()
            val args = Bundle().apply {
                putString(EXSTRA_ICON, currentIconAccount.code)
            }
            dialog.arguments = args
            return dialog
        }
    }

    override fun onIconClicked(iconAccount: IconAccount) {
        selectedNow = iconAccount
        onOk?.invoke()
    }
}