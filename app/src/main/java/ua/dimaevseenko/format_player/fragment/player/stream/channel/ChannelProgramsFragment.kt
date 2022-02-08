package ua.dimaevseenko.format_player.fragment.player.stream.channel

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.appComponent
import ua.dimaevseenko.format_player.databinding.FragmentProgramsBinding
import ua.dimaevseenko.format_player.model.Program
import ua.dimaevseenko.format_player.model.Programs
import ua.dimaevseenko.format_player.network.Server
import ua.dimaevseenko.format_player.network.result.ProgramsResult
import ua.dimaevseenko.format_player.viewmodel.ProgramsViewModel
import javax.inject.Inject

class ChannelProgramsFragment @Inject constructor(): Fragment(), Server.Listener<ProgramsResult>, ChannelProgramsRecyclerAdapter.Listener {

    companion object {
        const val TAG = "ChannelsProgramsFragment"
    }

    private lateinit var programsViewModel: ProgramsViewModel

    @Inject lateinit var recyclerAdapterFactory: ChannelProgramsRecyclerAdapter.Factory
    private lateinit var recyclerAdapter: ChannelProgramsRecyclerAdapter

    private lateinit var binding: FragmentProgramsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProgramsBinding.bind(inflater.inflate(R.layout.fragment_programs, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        programsViewModel = ViewModelProvider(requireActivity()).get(ProgramsViewModel::class.java)
        programsViewModel.addListener(TAG, this)

        initView()
    }

    private fun initView(){
        val stream = if(parentFragment is ChannelStreamFragment)
            (parentFragment as ChannelStreamFragment).getStream()
        else
            (parentFragment as ChannelControlsFragment).getStream()

        programsViewModel.getPrograms(stream.getStreamId())
    }

    override fun onResponse(result: ProgramsResult) {
        loadRecycler(result.requirePrograms())
    }

    override fun onFailure(t: Throwable) {

    }

    private fun loadRecycler(programs: Programs){
        recyclerAdapter = recyclerAdapterFactory.createChannelProgramsRecyclerAdapter(programs)
        recyclerAdapter.setListener(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.scrollToPosition(programs.getCurrentProgramPosition())
    }

    override fun onProgramSelected(program: Program, position: Int) {
        println(program)
    }

    override fun onVerticalFocusChanged(position: Int) {
        val lm = (binding.recyclerView.layoutManager as LinearLayoutManager)

        val first = lm.findFirstVisibleItemPosition()
        val last = lm.findLastVisibleItemPosition()

        val center = (last - first)/2

        val smoothScroller = getLinearSmoothScroller()

        if(position >= center) {
            smoothScroller.targetPosition = position-center
            lm.startSmoothScroll(smoothScroller)
        }
    }

    private fun getLinearSmoothScroller(): LinearSmoothScroller {
        return object : LinearSmoothScroller(context){
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
    }

    override fun onDestroy() {
        programsViewModel.removeListener(TAG)
        super.onDestroy()
    }
}