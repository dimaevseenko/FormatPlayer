package ua.dimaevseenko.format_player.fragment.player.navigation.profile.bonus.gift

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.RecyclerViewBonusGiftBuyHistoryItemBinding
import ua.dimaevseenko.format_player.model.Gift
import ua.dimaevseenko.format_player.model.Gifts

class GiftBuyHistoryRecyclerViewAdapter @AssistedInject constructor(
    @Assisted("gifts")
    private val gifts: Gifts,
    private val context: Context
): RecyclerView.Adapter<GiftBuyHistoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_bonus_gift_buy_history_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gifts[position])
    }

    override fun getItemCount(): Int {
        return gifts.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = RecyclerViewBonusGiftBuyHistoryItemBinding.bind(view)

        fun bind(gift: Gift){
            binding.buyHistoryTitle.text = gift.title
            binding.buyHistoryTimeTextView.text = gift.buyTime
            binding.buyHistoryPriceTextView.text = gift.price
            gift.code?.let { binding.buyHistoryCodeTextView.text = it }
            gift.issue?.let { binding.buyHistoryIssueTextView.text = it.issueDate }
        }
    }

    @AssistedFactory
    interface Factory{
        fun createAdapter(
            @Assisted("gifts")
            gifts: Gifts
        ): GiftBuyHistoryRecyclerViewAdapter
    }
}