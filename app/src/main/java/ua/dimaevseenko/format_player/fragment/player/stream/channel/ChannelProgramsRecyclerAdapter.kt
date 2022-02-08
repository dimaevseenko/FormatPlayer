package ua.dimaevseenko.format_player.fragment.player.stream.channel

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.RecyclerViewProgramDateItemBinding
import ua.dimaevseenko.format_player.databinding.RecyclerViewProgramItemBinding
import ua.dimaevseenko.format_player.model.Program
import ua.dimaevseenko.format_player.model.Programs

class ChannelProgramsRecyclerAdapter @AssistedInject constructor(
    @Assisted("programs")
    private val programs: Programs,
    private val context: Context
): RecyclerView.Adapter<ChannelProgramsRecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return if (viewType == 1)
            DateItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_program_date_item, parent, false)).apply { setIsRecyclable(false) }
        else
            ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_program_item, parent, false)).apply { setIsRecyclable(false) }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(programs[position], programs.getCurrentProgramId())
    }

    override fun getItemCount(): Int {
        return programs.size
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0)
            return 1

        if(programs[position].getDay() != programs[position-1].getDay())
            return 1

        return 0
    }

    override fun getItemId(position: Int): Long {
        return programs[position].gmtTime
    }

    open class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){

        private var binding = RecyclerViewProgramItemBinding.bind(view)

        open fun bind(program: Program, currentProgramId: Long) {
            binding.programTime.text = program.getTimeStart()
            binding.programName.text = program.name

            if(program.gmtTime == currentProgramId){
                binding.programTime.setTextColor(Color.WHITE)
                binding.programName.setTextColor(Color.WHITE)
            }
        }
    }

    class DateItemViewHolder(view: View): ItemViewHolder(view){
        private var binding = RecyclerViewProgramDateItemBinding.bind(view)

         override fun bind(program: Program, currentProgramId: Long) {
            super.bind(program, currentProgramId)
            binding.programDay.text = program.getDay()
        }
    }

    @AssistedFactory
    interface Factory{
        fun createChannelProgramsRecyclerAdapter(
            @Assisted("programs")
            programs: Programs
        ): ChannelProgramsRecyclerAdapter
    }
}