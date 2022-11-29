package com.example.android.finalproject.ui.user

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_POSITIVE
import android.content.DialogInterface.OnShowListener
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
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

            val dialog = builder.create()
            dialog.setOnShowListener {
                val button = dialog.getButton(BUTTON_POSITIVE)
                button.isEnabled = false
                val editWeight = dialogView.findViewById<EditText>(R.id.user_weight)
                val editHeightFt = dialogView.findViewById<EditText>(R.id.user_height_ft)
                val editHeightIn = dialogView.findViewById<EditText>(R.id.user_height_inch)

                val textWatcher = object: TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        button.isEnabled =
                            !(editWeight.text.isEmpty() || editHeightFt.text.isEmpty() || editHeightIn.text.isEmpty())
                                    && !(editWeight.text.toString().toDouble() < 1
                                    || editHeightFt.text.toString().toInt() < 1
                                    || editHeightIn.text.toString().toInt() < 1)
                    }

                }
                editWeight.addTextChangedListener(textWatcher)
                editHeightFt.addTextChangedListener(textWatcher)
                editHeightIn.addTextChangedListener(textWatcher)
            }
            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}