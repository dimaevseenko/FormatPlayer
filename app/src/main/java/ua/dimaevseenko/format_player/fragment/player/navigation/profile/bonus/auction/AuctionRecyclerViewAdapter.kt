package ua.dimaevseenko.format_player.fragment.player.navigation.profile.bonus.auction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.RecyclerViewBonusAuctionItemBinding
import ua.dimaevseenko.format_player.model.Auction
import ua.dimaevseenko.format_player.model.Auctions
import java.util.*

class AuctionRecyclerViewAdapter @AssistedInject constructor(
    @Assisted("auctions")
    private val auctions: Auctions,
    private val context: Context
): RecyclerView.Adapter<AuctionRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_bonus_auction_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, auctions[position])
    }

    override fun getItemCount(): Int {
        return auctions.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = RecyclerViewBonusAuctionItemBinding.bind(view)

        fun bind(context: Context, auction: Auction){
            binding.auctionTitleTextView.text = auction.title
            Glide.with(context).load(auction.img).into(binding.auctionImageView)
            binding.auctionStartPriceTextView.text = auction.startPrice
            binding.auctionTimeEndTextView.text = Date(auction.timeEnd).toString()
            binding.auctionDescriptionTextView.text = auction.note
            binding.imageView.setImageResource(R.drawable.ic_circle)
        }
    }

    @AssistedFactory
    interface Factory{
        fun createAdapter(
            @Assisted("auctions")
            auctions: Auctions
        ): AuctionRecyclerViewAdapter
    }
}