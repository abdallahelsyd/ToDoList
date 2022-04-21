package net.ibtikar.task.ui

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.ibtikar.task.data.local.Note
import net.ibtikar.task.repositories.NotesRepository
import net.ibtikar.task.utils.Constants
import net.ibtikar.task.utils.Event
import net.ibtikar.task.utils.Resource


class NotesViewModel @ViewModelInject constructor(
    private val repository: NotesRepository
) : AndroidViewModel(Application()) {

    val notes = repository.getNotes()
    private val _insertNoteDetails = MutableLiveData<Event<Resource<Note>>>()
    val insertNoteStatus: LiveData<Event<Resource<Note>>> = _insertNoteDetails

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    private fun insertNoteToDB(note: Note) = viewModelScope.launch {
        //repository.insertNote(note)
    }

    fun updateNote(note: Note, id: Int) = viewModelScope.launch {
        note.id = id
        repository.deleteNote(note)
    }

    fun insertNote(id: Int? = null, title: String, desc: String, date: String) {
        if (title.isEmpty() || desc.isEmpty() || date.isEmpty()) {
            _insertNoteDetails.postValue(
                Event(
                    Resource.error(
                        "The fields must not be empty",
                        null
                    )
                )
            )
            return
        }
        if (title.length > Constants.MAX_TITLE_LENGTH) {
            _insertNoteDetails.postValue(
                Event(
                    Resource.error(
                        "The name of the item" +
                                "must not exceed ${Constants.MAX_TITLE_LENGTH} characters", null
                    )
                )
            )
            return
        }
        if (desc.length > Constants.MAX_DESC_LENGTH) {
            _insertNoteDetails.postValue(
                Event(
                    Resource.error(
                        "The price of the item" +
                                "must not exceed ${Constants.MAX_DESC_LENGTH} characters", null
                    )
                )
            )
            return
        }
        val note = Note(id, title, desc, date)
        insertNoteToDB(note)
        _insertNoteDetails.postValue(Event(Resource.success(note)))
    }

    /////notification
    var chanelId = "chanelId"
    var chanelName = "chanelName"
    fun createNotfiChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chanel = NotificationChannel(
                chanelId,
                chanelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                vibrationPattern
            }


        }
    }


}