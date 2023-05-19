package com.example.restaurant.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.restaurant.databinding.DialogFragmentProgressBarBinding


class ProgressBarDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentProgressBarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFragmentProgressBarBinding.inflate(layoutInflater)
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
