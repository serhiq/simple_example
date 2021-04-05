
package com.gmail.uia059466.test_for_work_db.setting.currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.db.currency.UserCurrency

class CurrencyAdapter(private val listener: CurrencyListener)
  : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
  var isEditMode=false

   val data= mutableListOf<UserCurrency>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(
            R.layout.currency_item_list, parent,
            false
    )
   return ViewHolder(view)
  }

  fun setData(newList:List<UserCurrency>){

      val diffCallback = CurrencyDiffCallback(data, newList)
      val diffResult = DiffUtil.calculateDiff(diffCallback)
      data.clear()
      data.addAll(newList)
      diffResult.dispatchUpdatesTo(this)
  }

  override fun getItemCount() = data.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(data[position])
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title = itemView.findViewById<TextView>(R.id.currencies_title_tv)
    private val description = itemView.findViewById<TextView>(R.id.currencies_description_tv)
    private val deleteFl = itemView.findViewById<FrameLayout>(R.id.delete_fl)

    fun bind(currency: UserCurrency) {
        if (currency.isLockedCurrency()){
            title.text="Основная Валюта"
            description.text=currency.code
        }else{
            renderUserCurrency(currency)
        }
    }

      private fun renderUserCurrency(currency: UserCurrency) {
          if (isEditMode){
              deleteFl.visibility=View.VISIBLE
              deleteFl.setOnClickListener {
                  listener.onDeleteItem(currency)
              }
          }else{
              deleteFl.visibility=View.GONE
          }
          title.text=currency.description
          description.text=currency.code
      }
  }

    interface CurrencyListener {
        fun onDeleteItem(currency: UserCurrency)
    }

    private class CurrencyDiffCallback(private val oldList: List<UserCurrency>,
                                       private val newList: List<UserCurrency>) :
            DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int,
                                     newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList.get(newItemPosition).id
        }

        override fun areContentsTheSame(oldPosition: Int,
                                        newPosition: Int): Boolean {

            return oldList[oldPosition].code == newList[newPosition].code
        }


    }
}