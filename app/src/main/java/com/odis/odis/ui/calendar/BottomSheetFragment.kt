package com.odis.odis.ui.calendar

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.odis.odis.R
import com.odis.odis.Utils.*
import com.odis.odis.databinding.CalendarDialogLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class BottomSheetFragment(private val listener: OnDateChangeListener) :
    BottomSheetDialogFragment() {

    companion object {
       private const val FIXED_STARTED_YEAR = 1998
        private const val FIXED_MONTH = 0
        private const val FIXED_DAY_OF_MONTH = 1
        private  const val FIXED_STARTED_MONTH = 1
        private  const val FIXED_END_MONTH = 12
        private const val FIXED_STARTED_DAY = 1
        private  const val FIXED_END_DAY = 1
        private  const val FIXED_COUNT = 1
    }

    interface OnDateChangeListener {
        fun onDateChange(date: String)
    }


    private var _binding: CalendarDialogLayoutBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CalendarDialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFixedCalendar()

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.selectDateButton.setOnClickListener {
            val dateString = String.format(
                FIXED_STRING_FORMAT_FOR_DATE,
                binding.datePicker.year,
                binding.datePicker.month + 1,
                binding.datePicker.dayOfMonth
            )
            completionProcess(dateString)
        }

        binding.randomPhotoButton.setOnClickListener {
            val c = Calendar.getInstance()
            val currentYear = c.get(Calendar.YEAR)
            val dateString = String.format(
                FIXED_STRING_FORMAT_FOR_DATE,
                randBetween(FIXED_STARTED_YEAR, currentYear),
                randBetween(FIXED_STARTED_MONTH, FIXED_END_MONTH),
                randBetween(FIXED_STARTED_DAY, FIXED_END_DAY),
            )
            completionProcess(dateString)
        }

        binding.photoOfYesterdayButton.setOnClickListener {
            val c = Calendar.getInstance()
            c.add(Calendar.DAY_OF_MONTH, -FIXED_COUNT)
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dateString = String.format(
                FIXED_STRING_FORMAT_FOR_DATE,
                year,
                month + FIXED_COUNT,
                day
            )

            completionProcess(dateString)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val behavior = bottomSheetDialog.behavior
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }


    private fun completionProcess(dateString: String) {
        listener.onDateChange(dateString)
        dismiss()
    }

    private fun setFixedCalendar() {
        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, FIXED_STARTED_YEAR)
        c.set(Calendar.MONTH, FIXED_MONTH)
        c.set(Calendar.DAY_OF_MONTH, FIXED_DAY_OF_MONTH)

        binding.datePicker.minDate = c.timeInMillis
        binding.datePicker.maxDate = Date().time
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}