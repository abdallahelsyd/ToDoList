package net.ibtikar.task.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.ibtikar.task.data.local.Note
import net.ibtikar.task.repositories.NotesRepository

class FakeNotesRepository : NotesRepository {
    private val notes = mutableListOf<Note>()
    private val observableNote = MutableLiveData<List<Note>>(notes)
    private var checkNetworkError = false

    fun setCheckNetworkError(value: Boolean) {
        checkNetworkError = true
    }

    private fun refreshLiveData() {
        observableNote.postValue(notes)
    }

    override suspend fun insertNote(note: Note) {
        notes.add(note)
        refreshLiveData()
    }

    override fun getNotes(): LiveData<List<Note>> {
        return observableNote
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }


}