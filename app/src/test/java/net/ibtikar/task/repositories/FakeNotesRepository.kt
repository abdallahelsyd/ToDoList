package net.ibtikar.task.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.ibtikar.task.data.local.Note

class FakeNotesRepository : NotesRepository {
    private val notes = mutableListOf<Note>()
    private val observableNote = MutableLiveData<List<Note>>(notes)
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