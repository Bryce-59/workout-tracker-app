package com.example.android.finalproject.ui.user

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.android.finalproject.R

class MeasurementDialog(private val type: Int) : DialogFragment() {

    private lateinit var listener: MeasurementDialogListener

    interface MeasurementDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, view: View, type: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = requireParentFragment() as MeasurementDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement DialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_measurement_data, null)
            builder
                .setView(dialogView)
                .setPositiveButton("Save") { dialog, id ->
                    listener.onDialogPositiveClick(this, dialogView, type)
                }
                .setNegativeButton("Cancel") { _, _ ->
                    dialog?.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}