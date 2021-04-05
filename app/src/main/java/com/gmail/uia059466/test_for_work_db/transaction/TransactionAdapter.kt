package com.gmail.uia059466.test_for_work_db.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmail.uia059466.test_for_work_db.R

class TransactionAdapter(
        private val listener: Listener
                 ) : RecyclerView.Adapter<TransactionAdapter.BaseViewHolder<*>>() {

  val data= mutableListOf<DisplayTransaction>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
    return when (viewType) {
      ITEM_VIEW_TYPE_HEADER -> {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_caption, parent,
                false
                                                              )
        CaptionViewHolder(view)
      }
      ITEM_VIEW_TYPE_ITEM   -> {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_transaction, parent,
                false
                                                              )
        MainViewHolder(view)
      }
      else                  -> throw IllegalArgumentException("Invalid view type")
    }
  }

  private val ITEM_VIEW_TYPE_HEADER = 0
  private val ITEM_VIEW_TYPE_ITEM = 1


  override fun getItemViewType(position: Int): Int {
    return when {
      data[position] is DisplayTransaction.TransactionDisplay -> ITEM_VIEW_TYPE_ITEM
      else                                                    -> ITEM_VIEW_TYPE_HEADER
    }
  }

  override fun getItemCount(): Int {
    return data.size
  }

  override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
    val element = data[position]

    when {
      holder is CaptionViewHolder && element is DisplayTransaction.Header -> holder.bind(element)
      holder is MainViewHolder && element is DisplayTransaction.TransactionDisplay  -> holder.bind(element)

      else                                                           -> throw IllegalArgumentException()
    }
  }

  abstract class BaseViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
  }

  inner class CaptionViewHolder(itemView: View) : BaseViewHolder<DisplayTransaction.Header>(itemView) {
    private val captionTv: TextView = itemView.findViewById(R.id.text)

    override fun bind(item: DisplayTransaction.Header) {
      captionTv.text = item.title
    }
  }

  inner class MainViewHolder(itemView: View) : BaseViewHolder<DisplayTransaction.TransactionDisplay>(itemView) {
  
    private val tvDescription: TextView = itemView.findViewById(R.id.description_tv)
    private val tvAmount: TextView = itemView.findViewById(R.id.amount_tv)

    lateinit var currentItem: DisplayTransaction.TransactionDisplay
  
    override fun bind(item: DisplayTransaction.TransactionDisplay) {
      this.currentItem = item

      tvDescription.text = currentItem.title
      if (currentItem.title.isBlank()){
        tvDescription.text = if (currentItem.amount.contains("-")) "Расход" else "Доход"

      }
      tvAmount.text = currentItem.amount
    
      itemView.setOnClickListener { listener.onListClicked(currentItem.id) }
    }
  }

  interface Listener {
    fun onListClicked(id: Long)
  }

  fun setData(newList: List<DisplayTransaction>){

    val diffCallback = TransactionDiffCallback(data, newList)
    val diffResult = DiffUtil.calculateDiff(diffCallback)
    data.clear()
    data.addAll(newList)

    diffResult.dispatchUpdatesTo(this)
  }
  private class TransactionDiffCallback(private val oldList: List<DisplayTransaction>,
                                     private val newList: List<DisplayTransaction>) :
          DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int,
                                 newItemPosition: Int): Boolean {
      val new=newList[newItemPosition]
      val old=oldList[oldItemPosition]

      if (new is DisplayTransaction.TransactionDisplay&&old is DisplayTransaction.TransactionDisplay){
        return new.id == old.id
      }
      if (new is DisplayTransaction.Header&&old is DisplayTransaction.Header){
        return new.title == old.title
      }
      return false
    }

    override fun areContentsTheSame(oldPosition: Int,
                                    newPosition: Int): Boolean {

      val new = newList[newPosition]
      val old = oldList[oldPosition]

      if (new is DisplayTransaction.TransactionDisplay && old is DisplayTransaction.TransactionDisplay) {
        val isSameTitle = new.title == old.title
        val isSameId = new.id == old.id
        val isSameAmount = new.amount == old.amount
        return isSameAmount && isSameTitle && isSameId
      }

      if (new is DisplayTransaction.Header && old is DisplayTransaction.Header) {
        return new.title == old.title
      }
      return false
    }
  }
}
