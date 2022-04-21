package net.ibtikar.task.ui.fragments.addFragment


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.ibtikar.task.R
import net.ibtikar.task.data.local.Note
import net.ibtikar.task.getOrAwaitValue
import net.ibtikar.task.launchFragmentInHiltContainer
import net.ibtikar.task.repository.FakeNotesRepository
import net.ibtikar.task.ui.FragmentsFactory
import net.ibtikar.task.ui.NotesViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: FragmentsFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun clickInsertIntoDb_NoteIntoDb() {
        val testViewModel = NotesViewModel(FakeNotesRepository())
        launchFragmentInHiltContainer<AddFragment>(
            fragmentFactory = fragmentFactory
        ) {
            viewmodel = testViewModel
        }

        onView(withId(R.id.titleXML)).perform(replaceText("title"))
        onView(withId(R.id.desXML)).perform(replaceText("desc1"))
        onView(withId(R.id.dateXML)).perform(replaceText("5.5"))
        onView(withId(R.id.addUpdateBtnXML)).perform(click())

        assertThat(testViewModel.notes.getOrAwaitValue())
            .contains(Note(null, "title", "desc1", "5.5"))
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }
}











