package ua.dimaevseenko.format_player.fragment.player.navigation.home.cameras

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.RecyclerViewCamerasItemBinding
import ua.dimaevseenko.format_player.model.Cam
import ua.dimaevseenko.format_player.model.Cams

class RecyclerCamerasAdapter @AssistedInject constructor(
    @Assisted("cams")
    private val cams: Cams,
    private val context: Context
): RecyclerView.Adapter<RecyclerCamerasAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_cameras_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cams[position])
    }

    override fun getItemCount(): Int {
        return cams.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var binding = RecyclerViewCamerasItemBinding.bind(view)

        fun bind(cam: Cam){
            binding.cameraImageView.setImageBitmap(cam.imageBitmap)
            binding.cameraTextView.text = cam.name
        }
    }

    @AssistedFactory
    interface Factory{
        fun createCamerasRecyclerAdapter(
            @Assisted("cams")
            cams: Cams
        ): RecyclerCamerasAdapter
    }
}