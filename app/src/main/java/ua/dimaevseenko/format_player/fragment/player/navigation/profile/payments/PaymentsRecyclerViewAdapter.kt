package ua.dimaevseenko.format_player.fragment.player.navigation.profile.payments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.RecyclerViewPaymentItemBinding
import ua.dimaevseenko.format_player.model.Payment
import ua.dimaevseenko.format_player.model.Payments

class PaymentsRecyclerViewAdapter @AssistedInject constructor(
    @Assisted("payments")
    private var payments: Payments,
    private val context: Context
): RecyclerView.Adapter<PaymentsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_payment_item, parent, false))
    }

    fun updatePayments(newPayments: Payments){
        val diff = DiffUtil.calculateDiff(DiffUtils(this.payments, newPayments))
        this.payments = newPayments
        diff.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(payments[position])
    }

    override fun getItemCount(): Int {
        return payments.size
    }

    class ViewHolder constructor(val view: View): RecyclerView.ViewHolder(view){
        private val binding = RecyclerViewPaymentItemBinding.bind(view)

        fun bind(payment: Payment){
            binding.paymentTime.text = payment.date
            binding.paymentAmount.text = payment.amount
            binding.paymentNote.text = payment.note
        }
    }

    @AssistedFactory
    interface Factory{
        fun createAdapter(
            @Assisted("payments")
            payments: Payments
        ): PaymentsRecyclerViewAdapter
    }

    class DiffUtils(
        private val oldPayments: Payments,
        private val newPayments: Payments
    ): androidx.recyclerview.widget.DiffUtil.Callback(){

        override fun getOldListSize(): Int {
            return oldPayments.size
        }

        override fun getNewListSize(): Int {
            return newPayments.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldPayments[oldItemPosition] == newPayments[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldPayments[oldItemPosition].date == newPayments[newItemPosition].date
        }
    }
}