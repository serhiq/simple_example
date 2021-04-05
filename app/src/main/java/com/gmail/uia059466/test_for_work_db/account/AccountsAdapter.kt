package com.gmail.uia059466.test_for_work_db.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.account.accountedit.UserAccount
import java.math.BigDecimal

class AccountsAdapter(val listener: AccountListListener)
    : RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {
    val data = mutableListOf<UserAccount>()

    interface AccountListListener {
        fun onAccountClicked(id: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_account_icon, parent,
                false
        )
        return ViewHolder(view)
    }

    fun setData(newList: List<UserAccount>) {
        val diffCallback = CurrencyDiffCallback(data, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.title_account_tv)
        private val amount = itemView.findViewById<TextView>(R.id.amount_tv)

        private val icon = itemView.findViewById<ImageView>(R.id.icon_img)

        fun bind(account: UserAccount) {
            itemView.setOnClickListener {
                listener.onAccountClicked(account.id)
            }

            icon.setImageResource(account.iconAccount.resId)
            title.text = account.title
            amount.text = AmountFormatter.createAmountString(account.amount.divide(BigDecimal(100)))
        }
    }

    private class CurrencyDiffCallback(private val oldList: List<UserAccount>,
                                       private val newList: List<UserAccount>) :
            DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int,
                                     newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList.get(newItemPosition).id
        }

        override fun areContentsTheSame(oldPosition: Int,
                                        newPosition: Int): Boolean {
            val new = newList[newPosition]
            val old = oldList[oldPosition]
            val isSameIcon = new.iconAccount == old.iconAccount
            val isSameTitle = new.title == old.title
            val isSameId = new.id == old.id

            return isSameIcon && isSameTitle && isSameId
        }
    }
}