package net.ibtikar.task.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import net.ibtikar.task.getOrAwaitValue
import net.ibtikar.task.launchFragmentInHiltContainer
import net.ibtikar.task.ui.fragments.listFragment.NotesListFragment
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class NotesDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: NotesDatabase
    private lateinit var dao: NotesDao

    @Before
    fun setup() {
        hiltAndroidRule.inject()
        dao = database.notesDao()
    }

    @After
    fun finish() {
        database.close()
    }

    @Test
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<NotesListFragment> {

        }
    }

    @Test
    fun insertNote() = runBlockingTest {
        val note = Note(1, "t1", "description1", "1111")
        dao.insert(note)
        val allNotes = dao.getAllNotes().getOrAwaitValue()
        assertThat(allNotes.contains(note))
    }
}