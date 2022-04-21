package net.ibtikar.task.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.ibtikar.task.MainCoroutineRule
import net.ibtikar.task.getOrAwaitValueTest
import net.ibtikar.task.repositories.FakeNotesRepository
import net.ibtikar.task.utils.Constants
import net.ibtikar.task.utils.Status
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NotesViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    lateinit var viewModel: NotesViewModel


    @Before
    fun setup() {
        viewModel = NotesViewModel(FakeNotesRepository())
    }

    @Test
    fun insertEmptyNoteShouldReturnError() {
        viewModel.insertNote(2, "title1", "desc1", "1111")
        val value = viewModel.insertNoteStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun insertTooLongTextNoteShouldReturnError() {
        val title = buildString {
            for (i in 1..Constants.MAX_TITLE_LENGTH + 1)
                append(1)
        }
        viewModel.insertNote(null, "desc1", "1111", "")
        val value = viewModel.insertNoteStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }
}