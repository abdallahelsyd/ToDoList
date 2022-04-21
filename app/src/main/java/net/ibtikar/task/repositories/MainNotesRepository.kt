package net.ibtikar.task.repositories

import androidx.lifecycle.LiveData
import net.ibtikar.task.data.local.Note
import net.ibtikar.task.data.local.NotesDao
import javax.inject.Inject

class MainNotesRepository @Inject constructor(private val notesDao: NotesDao) : NotesRepository {
    override suspend fun insertNote(note: Note) {
        notesDao.insert(note)
    }

    override fun getNotes(): LiveData<List<Note>> {
        return notesDao.getAllNotes()
    }

    override suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(note)
    }
}