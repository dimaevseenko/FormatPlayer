package ua.dimaevseenko.format_player.fragment.player.navigation.home.cameras

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentCamerasBinding
import ua.dimaevseenko.format_player.fragment.player.navigation.AnimatedFragment
import ua.dimaevseenko.format_player.fragment.player.navigation.home.HomeFragment
import ua.dimaevseenko.format_player.model.Cam
import ua.dimaevseenko.format_player.playerFragment
import ua.dimaevseenko.format_player.removeFragment
import ua.dimaevseenko.format_player.viewmodel.PlaylistViewModel
import javax.inject.Inject

class CamerasFragment @Inject constructor(): AnimatedFragment(), RecyclerCamerasAdapter.Listener {

    companion object{
        const val TAG = "CamerasFragment"
    }

    private lateinit var binding: FragmentCamerasBinding

    private lateinit var playlistViewModel: PlaylistViewModel

    @Inject lateinit var recyclerCamerasAdapterFactory: RecyclerCamerasAdapter.Factory
    private var recyclerCamerasAdapter: RecyclerCamerasAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCamerasBinding.bind(inflater.inflate(R.layout.fragment_cameras, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        if(savedInstanceState == null)
            animateStartX()

        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)

        binding.backCard.setOnClickListener { dismiss() }
        loadRecycler()
    }

    private fun loadRecycler(){
        binding.recyclerView.layoutManager = getLayoutManager()
        binding.recyclerView.adapter = getAdapter().apply {
            setListener(this@CamerasFragment)
        }
    }

    override fun onSelectedCam(cam: Cam, position: Int) {
        playerFragment.startStream(cam)
    }

    private fun getAdapter(): RecyclerCamerasAdapter{
        if(recyclerCamerasAdapter == null)
            recyclerCamerasAdapter = recyclerCamerasAdapterFactory.createCamerasRecyclerAdapter(playlistViewModel.getCameras()!!)
        return recyclerCamerasAdapter!!
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager{
        return if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            LinearLayoutManager(requireContext())
        else
            GridLayoutManager(requireContext(), 2)
    }

    fun dismiss(){
        if(isAnimated) {
            animateEndX()
            (parentFragment as HomeFragment).homeWaysFragment()
        }
    }
}