package ua.dimaevseenko.format_player.alert

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ua.dimaevseenko.format_player.R
import ua.dimaevseenko.format_player.databinding.DialogFragmentProgressBinding
import javax.inject.Inject

class ProgressDialogFragment @Inject constructor(): DialogFragment() {

    companion object{
        const val TAG = "ProgressDialogFragment"
    }

    private lateinit var binding: DialogFragmentProgressBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Theme_FormatPlayer_DialogFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogFragmentProgressBinding.bind(inflater.inflate(R.layout.dialog_fragment_progress, container, false))
        return binding.root
    }
}