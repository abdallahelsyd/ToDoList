package net.ibtikar.task.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import net.ibtikar.task.data.local.NotesDao
import net.ibtikar.task.data.local.NotesDatabase
import net.ibtikar.task.repositories.MainNotesRepository
import net.ibtikar.task.repositories.NotesRepository
import net.ibtikar.task.utils.Constants.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        NotesDatabase::class.java,
        DATABASE_NAME
    ).build()


    @Provides
    @Singleton
    fun provideNotesDao(database: NotesDatabase) = database.notesDao()

    @Provides
    @Singleton
    fun provideMainNotesDao(dao: NotesDao) = MainNotesRepository(dao) as NotesRepository


}