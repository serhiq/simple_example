package com.gmail.uia059466.test_for_work_db.setting.currency

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.gmail.uia059466.test_for_work_db.R
import java.util.*

class SelectCurrencyFromAllDialogFragment : AppCompatDialogFragment() {

    var onOk: (() -> Unit)? = null
    lateinit var selectedNow: Currency
    val exsistListCurrency= mutableListOf<String>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val locales =getAllCurrencies().toList().filter { !exsistListCurrency.contains(it.currencyCode) }.sortedBy { it.currencyCode }
        val titlesCurrencyList=locales.map { it.currencyCode + "  ("+it.displayName+")"}

        return AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_select_currency))
            .setNegativeButton(android.R.string.cancel) { _, _ ->
            }
            .setSingleChoiceItems(
                titlesCurrencyList.toTypedArray(),
                1
            ) { dialog, position ->
                selectedNow = locales[position]
                onOk?.invoke()
                dialog.dismiss()
            }
            .create()
    }


    companion object {

        fun getAllCurrencies(): Set<Currency> {
            val toret: MutableSet<Currency> = HashSet()
            val locs = Locale.getAvailableLocales()
            for (loc in locs) {
                try {
                    val currency = Currency.getInstance(loc)
                    if (currency != null) {
                        toret.add(currency)
                    }
                } catch (exc: Exception) {
                    // Locale not found
                }
            }
            return toret
        }
    }
}