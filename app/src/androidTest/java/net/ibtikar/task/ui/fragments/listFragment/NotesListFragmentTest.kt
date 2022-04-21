package net.ibtikar.task.ui.fragments.listFragment

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.ibtikar.task.R
import net.ibtikar.task.adapters.NotesAdapter
import net.ibtikar.task.getOrAwaitValue
import net.ibtikar.task.launchFragmentInHiltContainer
import net.ibtikar.task.ui.NotesViewModel
import net.ibtikar.task.ui.TestNoteFragmentFactory
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class NotesListFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var testFragmentFactory: TestNoteFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun swipeNote_deleteItemInDb() {
        var testViewModel: NotesViewModel? = null
        launchFragmentInHiltContainer<NotesListFragment>(
            fragmentFactory = testFragmentFactory
        ) {
            testViewModel = viewModel
            viewModel?.insertNote(1, "title", "desc1", "111")

        }

        onView(withId(R.id.rvNotes)).perform(
            RecyclerViewActions.actionOnItemAtPosition<NotesAdapter.NotesViewHolder>(
                0,
                swipeLeft()
            )
        )
        assertThat(testViewModel?.notes?.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun addBtnToAddNoteNavigateToAddFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<NotesListFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.floatingBtn)).perform(click())
        verify(navController).navigate(
            NotesListFragmentDirections.actionNotesListFragmentToAddFragment()
        )
    }
}