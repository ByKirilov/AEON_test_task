package com.example.aeon_test_task.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aeon_test_task.R
import com.example.aeon_test_task.network.pojo.Payment

class PaymentListAdapter : ListAdapter<Payment, PaymentListAdapter.PaymentViewHolder>(PaymentComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return PaymentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val paymentDescriptionItemView: TextView = itemView.findViewById(R.id.description)
        private val paymentAmountItemView: TextView = itemView.findViewById(R.id.amount)
        private val paymentCurrencyItemView: TextView = itemView.findViewById(R.id.currency)

        fun bind(payment: Payment?) {
            paymentDescriptionItemView.text = payment?.description?:"---"
            paymentAmountItemView.text = (payment?.amount?:"---").toString()
            paymentCurrencyItemView.text = payment?.currency?:"---"
        }

        companion object {
            fun create(parent: ViewGroup): PaymentViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_item, parent, false)
                return PaymentViewHolder(view)
            }
        }
    }

    class PaymentComparator : DiffUtil.ItemCallback<Payment>() {
        override fun areContentsTheSame(oldItem: Payment, newItem: Payment): Boolean {
            return oldItem.description == newItem.description
                    && oldItem.amount == newItem.amount
                    && oldItem.currency == newItem.currency
        }

        override fun areItemsTheSame(oldItem: Payment, newItem: Payment): Boolean {
            return oldItem == newItem
        }
    }

    companion object Factory {
        private var adapter: PaymentListAdapter? = null
        fun getAdapter(): PaymentListAdapter {
            if (adapter == null) {
                adapter = PaymentListAdapter()
            }
            return adapter as PaymentListAdapter
        }
    }
}