package com.gmail.uia059466.test_for_work_db.account.accountedit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.uia059466.test_for_work_db.R
import com.gmail.uia059466.test_for_work_db.utls.getThemeColor

class AccountIconAdapter(val selected: IconAccount,
                         private val listener: IconListener)
    : RecyclerView.Adapter<AccountIconAdapter.ViewHolder>() {

    private val icons = IconAccount.values()

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_icon_account, parent,
                false
        )
        return ViewHolder(view)
    }

    override fun getItemCount() = icons.size

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        holder.bind(icons[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
                                             View.OnClickListener {

        private lateinit var iconAccount: IconAccount
        private val imageView = itemView.findViewById<ImageView>(R.id.icon)

        init {
            itemView.setOnClickListener(this)
        }

        private val colorBackground = itemView.getThemeColor(R.attr.big_space)
        private val colorSelected = itemView.getThemeColor(R.attr.color_secondary_alpha)

        fun bind(icon: IconAccount) {
            itemView.setBackgroundColor(colorBackground)
            this.iconAccount = icon
            imageView.setImageResource(icon.resId)
            if (icon == selected) {
                itemView.setBackgroundColor(colorSelected)
            } else {
                itemView.setBackgroundColor(colorBackground)

            }
        }

        override fun onClick(view: View) {
            listener.onIconClicked(this.iconAccount)
        }
    }

    interface IconListener {
        fun onIconClicked(iconAccount: IconAccount)
    }
}