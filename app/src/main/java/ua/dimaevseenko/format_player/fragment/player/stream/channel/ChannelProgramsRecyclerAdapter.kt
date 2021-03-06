package ua.dimaevseenko.format_player.fragment.player.stream.channel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.RecyclerViewProgramDateItemBinding
import ua.dimaevseenko.format_player.databinding.RecyclerViewProgramItemBinding
import ua.dimaevseenko.format_player.model.Program
import ua.dimaevseenko.format_player.model.Programs

class ChannelProgramsRecyclerAdapter @AssistedInject constructor(
    @Assisted("programs")
    private var programs: Programs,
    @Assisted("allowCatchup")
    private val allowCatchup: Boolean,
    private val context: Context
): RecyclerView.Adapter<ChannelProgramsRecyclerAdapter.ItemViewHolder>() {

    private var listener: Listener? = null

    fun setListener(listener: Listener){
        this.listener = listener
    }

    init{
        update()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun update(){
        CoroutineScope(Dispatchers.Default).launch {
            delay(60000)
            if (!allowCatchup)
                programs = programs.getProgramsWithoutPast()
            launch(Dispatchers.Main){
                notifyDataSetChanged()
                update()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return if (viewType == 1)
            DateItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_program_date_item, parent, false)).apply { setIsRecyclable(false) }
        else
            ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_program_item, parent, false)).apply { setIsRecyclable(false) }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(programs[position], programs.getCurrentProgramId(), programs[0].gmtTime, programs[programs.size-1].gmtTime, listener)
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

    interface Listener{
        fun onLiveProgramSelected(program: Program, position: Int)
        fun onCatchupProgramSelected(program: Program, position: Int)
        fun onVerticalFocusChanged(position: Int)
    }

    open class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){

        private var binding = RecyclerViewProgramItemBinding.bind(view)

        open fun bind(program: Program, currentProgramId: Long, firstProgramId: Long, lastProgramId: Long, listener: Listener?) {
            binding.programDate.text = "${program.getTimeStart()}   ${program.name}"

            binding.programDate.isClickable = true
            binding.programDate.setOnClickListener {
                if(program.gmtTime == currentProgramId)
                    listener?.onLiveProgramSelected(program, absoluteAdapterPosition)
                else if(program.gmtTime < currentProgramId)
                    listener?.onCatchupProgramSelected(program, absoluteAdapterPosition)
            }

            binding.programDate.setOnFocusChangeListener { _, hasFocus ->
                if(hasFocus)
                    listener?.onVerticalFocusChanged(absoluteAdapterPosition)
            }

            if(program.gmtTime == currentProgramId){
                binding.imageView6.setImageResource(R.drawable.ic_livvee)
                binding.programDate.setTextColor(Color.WHITE)
                binding.programDate.requestFocus()
            } else if(program.gmtTime < currentProgramId)
                binding.imageView6.setImageResource(R.drawable.ic_play)
            else
                binding.imageView6.setImageResource(R.drawable.ic_stop)

            if(program.gmtTime == firstProgramId)
                binding.programDate.apply { nextFocusUpId = id }

            if(program.gmtTime == lastProgramId)
                binding.programDate.apply { nextFocusDownId = id }

        }
    }

    class DateItemViewHolder(view: View): ItemViewHolder(view){
        private var binding = RecyclerViewProgramDateItemBinding.bind(view)

         override fun bind(program: Program, currentProgramId: Long, firstProgramId: Long, lastProgramId: Long, listener: Listener?) {
            super.bind(program, currentProgramId, firstProgramId, lastProgramId, listener)
            binding.programDay.text = program.getDay()
        }
    }

    @AssistedFactory
    interface Factory{
        fun createChannelProgramsRecyclerAdapter(
            @Assisted("programs")
            programs: Programs,
            @Assisted("allowCatchup")
            allowCatchup: Boolean
        ): ChannelProgramsRecyclerAdapter
    }
}