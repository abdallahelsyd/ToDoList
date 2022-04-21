package net.ibtikar.task.ui.fragments.addFragment

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add.*
import net.ibtikar.task.R
import net.ibtikar.task.notification.AlarmBroadcastReceiver
import net.ibtikar.task.ui.NotesViewModel
import net.ibtikar.task.ui.fragments.listFragment.NotesListFragmentArgs
import net.ibtikar.task.utils.Status
import java.text.SimpleDateFormat


@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add) {

    lateinit var calender: Calendar
    lateinit var viewmodel: NotesViewModel
    val args by navArgs<NotesListFragmentArgs>()

    //var note=args.selectedNote
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val note = args.selectedNote
        if (note.id == null) {
            addUpdateBtnXML.text = "Add New Note"
            titleXML.hint = "Title"
            desXML.hint = "Write description..."
        } else {
            addUpdateBtnXML.text = "Update this Note"
            titleXML.setText(note.title)
            desXML.setText(note.discription)
            dateXML.text = note.date
        }
        viewmodel = ViewModelProvider(requireActivity()).get(NotesViewModel::class.java)
        subscribeObserver()


        addUpdateBtnXML.setOnClickListener {
            viewmodel.insertNote(
                note.id,
                titleXML.text.toString(),
                desXML.text.toString(),
                dateXML.text.toString()
            )
        }


        ////////////////date and time Picker////////////////////
        calender = Calendar.getInstance()
        var resultDate = ""
        val time: TimePickerDialog.OnTimeSetListener =
            TimePickerDialog.OnTimeSetListener { tp, h, m ->
                calender.set(Calendar.HOUR, h)
                calender.set(Calendar.MINUTE, m)
                val timeStringformat: String = "hh:mm"
                val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(timeStringformat)
                resultDate += " ( " + simpleDateFormat.format(calender.time) + " )"
                dateXML.text = resultDate

            }
        val date: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { dp: DatePicker, year: Int, month: Int, day: Int ->
                calender.set(Calendar.YEAR, year)
                calender.set(Calendar.MONTH, month)
                calender.set(Calendar.DAY_OF_MONTH, day)
                dp.minDate = System.currentTimeMillis()
                val dateStringformat: String = "dd-MMM-yyyy"
                val sdfDate: SimpleDateFormat = SimpleDateFormat(dateStringformat)
                resultDate += sdfDate.format(calender.time)
                TimePickerDialog(
                    requireContext(),
                    time,
                    calender.get(Calendar.HOUR),
                    calender.get(Calendar.MINUTE),
                    true
                ).show()


            }
        dateXML.setOnClickListener {
            resultDate = ""
            val dialog = DatePickerDialog(
                requireContext(), date, calender
                    .get(Calendar.YEAR), calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.minDate = System.currentTimeMillis()
            dialog.show()
        }


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun subscribeObserver() {
        viewmodel.insertNoteStatus.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { res ->
                when (res.status) {
                    Status.SUCCESS -> {
                        Snackbar.make(
                            requireView().rootView,
                            "Successfully Added",
                            Snackbar.LENGTH_LONG
                        ).show()
                        startAlarmBroadcastReceiver(requireContext())
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            requireView().rootView, res.message ?: "Something Wrong",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    Status.LOADING -> {
                        //do nothing
                    }

                }
            }
        })
    }

    private fun startAlarmBroadcastReceiver(context: Context) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        val notificalendar = Calendar.getInstance()
        notificalendar.timeInMillis = calender.timeInMillis - 60000
        alarmManager[AlarmManager.RTC_WAKEUP, notificalendar.timeInMillis] = pendingIntent
    }
}