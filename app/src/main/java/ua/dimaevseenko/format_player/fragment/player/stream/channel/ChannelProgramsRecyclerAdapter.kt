package ua.dimaevseenko.format_player.fragment.player.stream.channel

import android.content.Context
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
): RecyclerView.Adapter<ChannelProgramsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 0)
            ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_program_item, parent, false))
        else
            DateItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_program_date_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(programs[position])
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

    abstract class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        abstract fun bind(program: Program)
    }

    open class ItemViewHolder(view: View): ViewHolder(view){

        private var binding = RecyclerViewProgramItemBinding.bind(view)

        override fun bind(program: Program) {
            binding.programTime.text = program.getTimeStart()
            binding.programName.text = program.name
        }
    }

    class DateItemViewHolder(view: View): ItemViewHolder(view){
        private var binding = RecyclerViewProgramDateItemBinding.bind(view)

        override fun bind(program: Program) {
            super.bind(program)
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