package net.ibtikar.task.repositories

import androidx.lifecycle.LiveData
import net.ibtikar.task.data.local.Note

interface NotesRepository {
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun getNotes(): LiveData<List<Note>>
}