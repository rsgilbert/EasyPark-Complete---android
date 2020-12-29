//package com.gilboot.easypark.dialogs
//
//import android.app.AlertDialog
//import android.app.Dialog
//import android.app.TimePickerDialog
//import android.content.Context
//import android.content.DialogInterface
//import android.os.Bundle
//import android.text.format.DateFormat.is24HourFormat
//import android.view.LayoutInflater
//import android.widget.TimePicker
//import androidx.fragment.app.DialogFragment
//import com.gilboot.easypark.databinding.DialogNumberplateBinding
//import com.gilboot.easypark.util.withAddVisit
//import org.jetbrains.anko.support.v4.toast
//import java.lang.IllegalStateException
//
//class NumberplateDialog : DialogFragment() {
//
//    lateinit var numberplateListener: NumberplateListener
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//            val binding = DialogNumberplateBinding.inflate(LayoutInflater.from(requireContext()))
//            builder.setView(binding.root)
//            builder.setPositiveButton("Add") { _: DialogInterface, _: Int ->
//                run {
//                    withAddVisit(binding.numberplate.text.toString()) {
//                    }
//                }
//            }
//            return builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }
//
//
////    override fun onAttach(context: Context) {
////        super.onAttach(context)
////        try {
////            numberplateListener = parentFragment as NumberplateListener
////        } catch (e: ClassCastException) {
////            throw ClassCastException("$context must implement listener")
////        }
////    }
//}
//
