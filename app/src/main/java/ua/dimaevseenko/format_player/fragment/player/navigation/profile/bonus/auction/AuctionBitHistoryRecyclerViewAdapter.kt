package ua.dimaevseenko.format_player.fragment.player.navigation.profile.bonus.auction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.RecyclerViewBonusAuctionBitHistoryItemBinding
import ua.dimaevseenko.format_player.model.Auction
import ua.dimaevseenko.format_player.model.Auctions

class AuctionBitHistoryRecyclerViewAdapter @AssistedInject constructor(
    @Assisted("auctions")
    private val auctions: Auctions,
    private val context: Context
): RecyclerView.Adapter<AuctionBitHistoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_bonus_auction_bit_history_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(auctions[position])
    }

    override fun getItemCount(): Int {
        return auctions.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = RecyclerViewBonusAuctionBitHistoryItemBinding.bind(view)

        fun bind(auction: Auction){
            binding.bitHistoryTitle.text = auction.title
            binding.bitHistoryTimeTextView.text = auction.betTime
            binding.bitHistoryBitTextView.text = auction.lastBet?.betAmount
            binding.bitHistoryStatusTextView.text = getStatus(auction.status!!)
            auction.issue?.let {
                binding.bitHistoryCodeTextView.text = it.code
                binding.bitHistoryIssueTextView.text = it.issueDate
            }
        }

        private fun getStatus(status: Int): String{
            return "sss"
        }
    }

    @AssistedFactory
    interface Factory{
        fun createAdapter(
            @Assisted("auctions")
            auctions: Auctions
        ): AuctionBitHistoryRecyclerViewAdapter
    }
}