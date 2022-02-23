package ua.dimaevseenko.format_player.fragment.player.navigation.profile.bonus.gift

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
import ua.dimaevseenko.format_player.databinding.RecyclerViewBonusGiftItemBinding
import ua.dimaevseenko.format_player.model.Gift
import ua.dimaevseenko.format_player.model.Gifts

class GiftsRecyclerViewAdapter @AssistedInject constructor(
    @Assisted("gifts")
    private val gifts: Gifts,
    private val context: Context
): RecyclerView.Adapter<GiftsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_bonus_gift_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, gifts[position])
    }

    override fun getItemCount(): Int {
        return gifts.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = RecyclerViewBonusGiftItemBinding.bind(view)

        fun bind(context: Context, gift: Gift){
            binding.giftTitleTextView.text = gift.title
            Glide.with(context).load(gift.img).into(binding.giftImageView)
            binding.giftCostTextView.text = gift.price
            binding.giftCountTextView.text = gift.count
            binding.giftDescriptionsTextView.text = gift.note
            binding.imageView.setImageResource(R.drawable.ic_circle)
        }
    }

    @AssistedFactory
    interface Factory{
        fun createAdapter(
            @Assisted("gifts")
            gifts: Gifts
        ): GiftsRecyclerViewAdapter
    }
}