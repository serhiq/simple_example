package com.gmail.uia059466.test_for_work_db.setting.currencyrate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.db.rates.RatesUser

class CurrencyRateAdapter(private val listener: CurrencyListener)
    : RecyclerView.Adapter<CurrencyRateAdapter.ViewHolder>() {
    var isEditMode = false

    val data = mutableListOf<RatesUser>()

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.rate_currency_item_list, parent,
                false
        )
        return ViewHolder(view)
    }

    fun setData(newList: List<RatesUser>) {
        data.clear()
        data.addAll(newList)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.currencies_title_tv)
        private val deleteFl = itemView.findViewById<FrameLayout>(R.id.delete_fl)

        fun bind(currency: RatesUser) {
            renderUserCurrency(currency)

        }

        private fun renderUserCurrency(rate: RatesUser) {
            if (isEditMode) {
                deleteFl.visibility = View.VISIBLE
                deleteFl.setOnClickListener {
                    listener.onDeleteItem(rate)
                }
            } else {
                deleteFl.visibility = View.GONE
            }
            title.text = rate.createDescriptionForList()
        }
    }

    interface CurrencyListener {
        fun onDeleteItem(currency: RatesUser)
    }
}