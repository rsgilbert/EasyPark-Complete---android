package com.gilboot.easypark.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ScanDialog : DialogFragment() {
    lateinit var scanListener: ScanListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setItems(arrayOf("Arriving", "Departing")) { _, which: Int ->
                when (which) {
                    0 -> scanListener.scanArrival()
                    1 -> scanListener.scanDeparture()
                }
            }

            return builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            scanListener = parentFragment as ScanListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement listener")
        }
    }
}

