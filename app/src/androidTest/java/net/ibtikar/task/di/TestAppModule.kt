package net.ibtikar.task.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import net.ibtikar.task.data.local.NotesDatabase
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
class TestAppModule {
    @Provides
    @Named("test_db")
    fun provideRoomDB(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, NotesDatabase::class.java)
            .allowMainThreadQueries()
            .build()


}